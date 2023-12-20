import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchElementException
from bs4 import BeautifulSoup
#import openpyxl
#from pathlib import Path
#from io import BytesIO
#import dload
from time import sleep
from datetime import datetime
import MySQLdb
#import subprocess
import requests

time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

def find_element_by_css_selector(driver, css_selector):
    try:
        return driver.find_element(By.CSS_SELECTOR, 'css_selector')
    except NoSuchElementException as e:
        return None

def driver_get(url):
    option = Options()
    options.add_argument("disable-gpu")
    options.add_argument("lang=ko_KR")
    options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')
    driver = webdriver.Chrome(options=option)
    driver.implicitly_wait(10)
    driver.set_window_size(1400,800)
    driver.get(url)
    return driver

#DB연결
conn = MySQLdb.connect(
    user="root",
    passwd="1937",
    host="localhost",
    db="member"
)
cursor = conn.cursor()

# 실행할 때마다 다른값이 나오지 않게 테이블을 제거해두기
cursor.execute("DROP TABLE IF EXISTS gamedata_epic_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_epic")

#rank에 AUTO_INCREMENT를 사용함으로써 INSERT가 입력될때마다 자동으로 숫자를 +1 올린다
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_epic (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                            `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                            `IMGDATA` TEXT NULL DEFAULT NULL,
                                                            `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                            `URL` TEXT NULL DEFAULT NULL,
                                                            PRIMARY KEY (`NUM`),
                                                            UNIQUE KEY (`TITLE`))
            ''')

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_epic_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                 `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                 `GENRE` VARCHAR(30) NULL DEFAULT NULL,
                                                                 PRIMARY KEY (`NUM`),
                                                                 CONSTRAINT `epic_title`
                                                                    FOREIGN KEY (`TITLE`)
                                                                    REFERENCES `gamedata_epic` (`TITLE`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE)
               ''')

URL = 'https://store.epicgames.com/ko/'
epicgames = 'https://store.epicgames.com'
platform = 'epicgames'

services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_argument("disable-gpu")
options.add_argument("lang=ko_KR")
options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')

driver = webdriver.Chrome(service=services, options=options)
driver.implicitly_wait(5)
driver.set_window_size(1400,800)
driver.get(URL)

soup = BeautifulSoup(driver.page_source, "html.parser")
sale_url_find = soup.select_one('a.group-swiper-slider--title-link')["href"]
sale_url = epicgames + sale_url_find

driver.quit()
driver = driver_get(sale_url)
sleep(3)

page = driver.find_element(By.TAG_NAME, "body")

for _ in range(0,8):
    page.send_keys(Keys.PAGE_DOWN)
    sleep(0.5)

#할인 페이지 들어오자마자 페이지바 읽어와서 마지막 페이지 값 저장
pages = driver.find_elements(By.CLASS_NAME, "css-12lid1g")
#다음페이지 초기값(숫자)
next_page = 2
#마지막페이지 값(문자열)
last_page = pages[6].text
last_game_title = "title"

for _ in range(10000000000):
    soup = BeautifulSoup(driver.page_source, "html.parser")
    print("1.페이지 내 게임 패널 읽어오는중")
    panel = soup.select_one("section.css-zjpm9r")
    gamelist = panel.select("li.css-lrwy1y")
    
    driver.quit()
    for item in gamelist:
        print("2.패널 속 게임 데이터 크롤링")
        title = item.find("div", class_="css-rgqwpc")
        if title == None:
            title = item.find("div",class_="css-8uhtka").text
        else:
            title = title.text

        #직전 크롤링한 게임의 타이틀과 현재 크롤링중인 게임의 타이틀이 동일하면 마지막페이지라서 break
        if last_game_title == title:
            break

        saleper = item.find("div", class_="css-b0xoos")
        if saleper != None:
            price = item.find("div", class_="css-4jky3p").text
            saleprice = item.find("span", class_="css-119zqif").text
            saleper = saleper.text
        else:
            saleper = "X"
            saleprice = "X"

        game_link = item.select_one('a.css-g3jcms')["href"]
        move = epicgames+game_link

        driver = driver_get(move)
        sleep(3)
        new_soup = BeautifulSoup(driver.page_source, "html.parser")

        try:
            description = new_soup.select_one("div.css-1myreog")
            if description != None:
                description = description.text.strip()
        except:
            print("게임 설명 크롤링 오류 NULL로 대체")

        try:
            imgdata = new_soup.select_one('img.css-7i770w')["src"]
        except:
            pass
        
        try:
            gameimg_bar = new_soup.select_one('ul.css-elmzlf')
            if gameimg_bar != None:
                gameimg = gameimg_bar.select_one('div.css-1q03292 > img')["src"]
            else:
                gameimg = new_soup.select_one('img.css-1bbjmcj')["src"]
        except:
            pass

        tag = new_soup.select("li.css-t8k7")
        tag_length = len(tag)
        num = 0

        sql = 'INSERT INTO gamedata_epic (TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL) VALUES (%s, %s, %s, %s, %s, %s, %s ,%s)'
        cursor.execute(sql, (title, price, saleprice, saleper, description, imgdata, gameimg, move))

        while num < tag_length:
            tag[num] = tag[num].text.strip()
            sql = 'INSERT INTO gamedata_epic_genre (TITLE, GENRE) VALUES (%s, %s)'
            cursor.execute(sql, (title, tag[num]))
            num += 1

        conn.commit()
        print(f'epic - {title} DB 입력 완료')
        driver.quit()

        #현재 페이지가 마지막 페이지면 last_game_title에 지금 크롤링중인 게임 타이틀 집어넣기
        if next_page-1 == int(last_page):
            last_game_title = title
        sleep(1)
    
    if int(last_page)+1 == next_page:
        print("5.마지막페이지 크롤링 완료")
        break

    #페이지 이동 실행하는 부분
    print("3.페이지 변경")
    sleep(1)
    pagebar = soup.select_one('ul.css-zks4l')
    pages = pagebar.select('a.css-1ns6940')
    for page in pages:
        if page.text == str(next_page):
            move_url = page["href"]
            move = epicgames + move_url
            next_page += 1
            driver.quit()
            driver = driver_get(move)
            print("3.페이지 변경 완료")
            print("5초 대기")
            sleep(5)
            break

    page = driver.find_element(By.TAG_NAME, "body")
    for _ in range(0,8):
        print("4.스크롤 내리는중")
        page.send_keys(Keys.PAGE_DOWN)
        sleep(0.5)

newtime = datetime.now()
newtimestr = newtime.strftime("%Y%m%d_%H%M")
sleep(2)
print(platform, " 크롤링 완료 - 시작시간:", timestr, ", 완료시간:", newtimestr)

print("크롤링 끝 driver 종료")
driver.quit()