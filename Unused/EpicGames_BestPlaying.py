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
#from datetime import datetime
import MySQLdb
#import subprocess
import requests

def find_element_by_css_selector(driver, css_selector):
    try:
        return driver.find_element(By.CSS_SELECTOR, 'css_selector')
    except NoSuchElementException as e:
        return None

'''
Cloudflare를 우회하기위해서 크롬을 디버그모드로 열었었는데 다시 실행해보니 그게 막혀있길래 인터넷을 좀 찾아보니
Cloudflare는 셀레니움으로 크롤링을 할때 url에 변동이있으면 디도스로 인식하여 인증해야지 페이지 이동이 가능해진다고한다
그래서 생각해본게 페이지를 이동할때 아예 크롬을 껏다가 게임페이지 url로 재실행하면 어떨까해서 그렇게 실행해봤더니 인증화면이 안나온다
시간은 좀 걸리지만 에픽게임즈는 이방법으로 진행해야 크롤링이 가능할거같다
'''
def driver_get(url):
    option = Options()
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
cursor.execute("DROP TABLE IF EXISTS gamedata_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_info")

#rank에 AUTO_INCREMENT를 사용함으로써 INSERT가 입력될때마다 자동으로 숫자를 +1 올린다
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_info (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PLATFORM` VARCHAR(10) NULL DEFAULT NULL,
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

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                             `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                             `PLATFORM` VARCHAR(10) NULL DEFAULT NULL,
                                                             `GENRE` VARCHAR(30) NULL DEFAULT NULL,
                                                             PRIMARY KEY (`NUM`),
                                                             CONSTRAINT `game_title`
                                                                FOREIGN KEY (`TITLE`)
                                                                REFERENCES `gamedata_info` (`TITLE`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE CASCADE)
               ''')

URL = 'https://store.epicgames.com/ko/collection/most-played'
epicgames = 'https://store.epicgames.com'
platform = 'epicgames'

#크롬 디버그 모드로 열기
#chrome = subprocess.Popen(r'C:\Program Files\Google\Chrome\Application\chrome.exe --remote-debugging-port=9222 --user-data-dir="C:\chromeCookie"')

#크롬드라이버 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())
options = Options()
#options.add_experimental_option("debuggerAddress", '127.0.0.1:9222')
options.add_argument("disable-gpu")   # 가속 사용 x
options.add_argument("lang=ko_KR")    # 가짜 플러그인 탑재
options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')  # user-agent 이름 설정

#크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(service=services, options=options)
#driver.execute_script("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")
sleep(3)
driver.implicitly_wait(5)
driver.set_window_size(1400,800)
driver.get(URL)

soup = BeautifulSoup(driver.page_source, "html.parser")
page = driver.find_element(By.TAG_NAME, "body")

for _ in range(0,20):
    page.send_keys(Keys.PAGE_DOWN)
    sleep(0.5)

soup = BeautifulSoup(driver.page_source, "html.parser")

#게임 데이터 크롤링
panel = soup.select_one("section.css-zjpm9r")
gamelist = panel.select("div.css-lrwy1y")

driver.quit()
for item in gamelist:
    title = item.find("div", class_="css-rgqwpc")

    if title == None:
        title = item.find("div",class_="css-8uhtka").text
    else:
        title = title.text

    price = item.find("span", class_="css-119zqif")
    if price == None:
        price = "정보 없음"
    else:
        price = price.text
    
    saleper = item.find("div", class_="css-b0xoos")
    if saleper != None:
        price = item.find("div", class_="css-4jky3p").text
        saleprice = item.find("span", class_="css-119zqif").text
        saleper = saleper.text
    else:
        saleper = "X"
        saleprice = "X"

    #move 변수에 해당 게임 페이지 링크 획득
    game_link = item.select_one('a.css-g3jcms')["href"]
    move = epicgames+game_link

    #게임 페이지로 새탭에서 열기
    driver = driver_get(move)
    #driver.execute_script(f'window.open(\'{move}\');')
    #새탭으로 이동
    #driver.switch_to.window(driver.window_handles[-1])

    new_soup = BeautifulSoup(driver.page_source, "html.parser")

    description = new_soup.select_one("div.css-1myreog")
    if description != None:
        description = description.text.strip()

    imgdata = new_soup.select_one('img.css-7i770w')["src"]
    
    gameimg_bar = new_soup.select_one('ul.css-elmzlf')
    if gameimg_bar != None:
        gameimg = gameimg_bar.select_one('div.css-1q03292 > img')["src"]
    else:
        gameimg = new_soup.select_one('img.css-1bbjmcj')["src"]

    print(title)
    print(gameimg)

    tag = new_soup.select("li.css-t8k7")

    tag_length = len(tag)
    num = 0

    sql = 'INSERT INTO gamedata_info (TITLE, PLATFORM, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL) VALUES (%s, %s, %s, %s, %s, %s, %s, %s ,%s)'
    cursor.execute(sql, (title, platform, price, saleprice, saleper, description, imgdata, gameimg, move))

    while num < tag_length:
        tag[num] = tag[num].text.strip()
        sql = 'INSERT INTO gamedata_genre (TITLE, PLATFORM, GENRE) VALUES (%s, %s, %s)'
        cursor.execute(sql, (title, platform, tag[num]))
        print(tag[num])
        num += 1

    conn.commit()
    print(f'{title} DB 입력 완료')

    #탭 종료후 원래 탭으로 이동
    #driver.close()
    #driver.switch_to.window(driver.window_handles[-1])
    driver.quit()

sleep(2)
print("크롤링 완료")
#chrome.kill()
#driver.quit()

"""
#액셀 인스턴스 생성, 경로 지정
excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active

row_column = ["순위", "게임명", "정가", "할인가", "할인율", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\Epic_BestPlaying{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

i = 1

for item in gamelist:
    title = item.find("div", class_="css-rgqwpc")
    if title == None:
        title = item.find("div",class_="css-8uhtka")
    price = item.find("span", class_="css-119zqif")
    if price == None:
        price = "정보 없음"
    else:
        price = price.text
    
    saleper = item.find("div", class_="css-b0xoos")
    if saleper != None:
        price = item.find("div", class_="css-4jky3p").text
        saleprice = item.find("span", class_="css-119zqif").text
        saleper = saleper.text
    else:
        saleper = "X"
        saleprice = "X"
    
    img = item.find("img", class_="css-174g26k")
    imgdata = img["src"]

    data_column = [i, title.text, price, saleprice, saleper, imgdata]
    excel_sheet.append(data_column)
    dload.save(imgdata, f'{path}\IMG_{i}.jpg')
    i += 1

#액셀 저장 후 종료
excel_File.save(f'{path}\Epic_BestPlaying{timestr}.xlsx')
excel_File.close()

#모든 작업이 완료되면 5초 대기했다가 인스턴스 해제
driver.quit()
"""