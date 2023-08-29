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
import subprocess
import requests

def find_element_by_css_selector(driver, css_selector):
    try:
        return driver.find_element(By.CSS_SELECTOR, 'css_selector')
    except NoSuchElementException as e:
        return None

#DB연결
conn = MySQLdb.connect(
    user="crawler",
    passwd="crawler1937",
    host="localhost",
    db="crawling_test"
)
cursor = conn.cursor()

# 실행할 때마다 다른값이 나오지 않게 테이블을 제거해두기
cursor.execute("DROP TABLE IF EXISTS epic_best_playing_genre")
cursor.execute("DROP TABLE IF EXISTS epic_best_playing")

#steam_best 테이블 생성
#rank에 AUTO_INCREMENT를 사용함으로써 INSERT가 입력될때마다 자동으로 숫자를 +1 올린다
cursor.execute('''CREATE TABLE epic_best_playing (
                                           num int(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           title varchar(100) NOT NULL UNIQUE KEY,
                                           price varchar(30) NOT NULL,
                                           saleprice varchar(30),
                                           saleper varchar(30),
                                           description varchar(500),
                                           imgdata varchar(3000)
                                           )
                                           ''')

cursor.execute('''CREATE TABLE epic_best_playing_genre (
                                           num INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                           title varchar(100) NOT NULL,
                                           genre varchar(20) NOT NULL,
                                           FOREIGN KEY(title) REFERENCES epic_best_playing(title)
                                           )
                                           ''')

URL = 'https://store.epicgames.com/ko/collection/most-played'

subprocess.Popen(r'C:\Program Files\Google\Chrome\Application\chrome.exe --remote-debugging-port=9222 --user-data-dir="C:\chromeCookie"')

#크롬드라이버 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_experimental_option("debuggerAddress", '127.0.0.1:9222')

#크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(service=services, options=options)
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
    
    img = item.find("img", class_="css-174g26k")
    imgdata = img["src"]

    #move 변수에 해당 게임 페이지 링크 획득
    game_link = item.select_one('a.css-g3jcms')
    move = game_link["href"]

    #게임 페이지로 새탭에서 열기
    driver.execute_script(f'window.open(\'{move}\');')
    #새탭으로 이동
    driver.switch_to.window(driver.window_handles[-1])

    new_soup = BeautifulSoup(driver.page_source, "html.parser")

    description = new_soup.select_one("div.css-1myreog")
    if description != None:
        description = description.text.strip()
    tag = new_soup.select("li.css-t8k7")

    tag_length = len(tag)
    num = 0

    sql = 'INSERT INTO epic_best_playing (title, price, saleprice, saleper, description, imgdata) VALUES (%s, %s, %s, %s, %s, %s)'
    cursor.execute(sql, (title, price, saleprice, saleper, description, imgdata))

    while num < tag_length:
        tag[num] = tag[num].text.strip()
        sql = 'INSERT INTO epic_best_playing_genre (title, genre) VALUES (%s, %s)'
        cursor.execute(sql, (title, tag[num]))
        num += 1

    conn.commit()
    print(f'{title} db에 입력완료')

    #탭 종료후 원래 탭으로 이동
    driver.close()
    driver.switch_to.window(driver.window_handles[-1])

sleep(2)
print("크롤링 완료")
driver.close()

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