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
import requests

def saleper_calc(price, saleprice):
    price = int(price.strip('₩ ').replace(',', ''))
    saleprice = int(saleprice.strip('₩ ').replace(',', ''))
    _ = price - saleprice
    __ = int((_ / price) * 100)
    saleper = "-"+str(__)+"%"
    return saleper

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

URL = 'https://store.nintendo.co.kr/games/sale'
platform = 'nintendo'

#크롬드라이버 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_experimental_option("detach", True)
options.add_argument("disable-gpu")
options.add_argument("lang=ko_KR")
options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')
options.add_argument("headless")

driver = webdriver.Chrome(service=services, options=options)
driver.implicitly_wait(5)
driver.set_window_size(1400,800)
driver.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument", {"source": """ Object.defineProperty(navigator, 'webdriver', { get: () => undefined }) """})
driver.get(URL)

sleep(2)
driver.find_element(By.CLASS_NAME, 'popup-close').click()
sleep(2)
page = driver.find_element(By.TAG_NAME, "body")
for _ in range(0,130):
    page.send_keys(Keys.PAGE_DOWN)
    sleep(0.3)

soup = BeautifulSoup(driver.page_source, "html.parser")

#게임 데이터 크롤링
panel = soup.select_one("div.category-product-list")
gamelist = panel.select("div.category-product-item")

i=0
#l=0
for item in gamelist:
    title = item.find("a", class_="category-product-item-title-link").text.strip()
    price = item.find("span", attrs={"data-price-type" : "oldPrice"})
    saleprice = item.find("span", attrs={"data-price-type" : "finalPrice"})
    img = item.find("img", class_="product-image-photo mplazyload mplazyload-transparent entered loaded")
    imgdata = img["src"]

    if price == None:
        price = item.find("span", class_="point-icon-wrapper")
        price = price.text + "포인트"
        saleprice = "포인트로 구매 가능"
        saleper = "X"
    else:
        price = price.find("span").text
        saleprice = saleprice.find("span").text
        saleper = saleper_calc(price, saleprice)

    game_link = item.select_one('a')
    move = game_link["href"]
    
    """
    if l > 120:
        print("5분간 일시정지(403 forbidden 오류 회피를 위해)")
        sleep(300)
        print("실행")
        l=0
    """

    driver.execute_script(f'window.open(\'{move}\');')
    driver.switch_to.window(driver.window_handles[-1])
    sleep(1.5)
    new_soup = BeautifulSoup(driver.page_source, "html.parser")

    error = new_soup.select_one('h1')
    if error.text == '403 Forbidden':
        print("5분간 일시정지(403 forbidden 오류 회피를 위해)")
        driver.close()
        driver.switch_to.window(driver.window_handles[-1])
        sleep(300)
        print("재실행")
        driver.execute_script(f'window.open(\'{move}\');')
        driver.switch_to.window(driver.window_handles[-1])
        sleep(5)
        new_soup = BeautifulSoup(driver.page_source, "html.parser")


    description = new_soup.select_one("div.game_ex")
    if description == None:
        description = new_soup.select_one("div.value")
    else:
        description = None
    
    if description != None:
        description = description.text.strip()

    gameimg = new_soup.select_one('img.fotorama__img')
    if gameimg == None:
        print("gameimg 오류 3초 대기후 재크롤링")
        sleep(3)
        new_soup = BeautifulSoup(driver.page_source, "html.parser")
        gameimg = new_soup.select_one('img.fotorama__img')
    gameimg = gameimg["src"]

    sql = 'INSERT INTO gamedata_info (TITLE, PLATFORM, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL) VALUES (%s, %s, %s, %s, %s, %s, %s, %s ,%s)'
    cursor.execute(sql, (title, platform, price, saleprice, saleper, description, imgdata, gameimg, move))

    tagparent = new_soup.find("div", class_="product-attribute game_category")
    if tagparent != None:
        tag = tagparent.find("div", class_="product-attribute-val").text.split(',')
        tag_length = len(tag)
        num = 0

        while num < tag_length:
            tag[num] = tag[num].strip()
            sql = 'INSERT INTO gamedata_genre (TITLE, PLATFORM, GENRE) VALUES (%s, %s, %s)'
            cursor.execute(sql, (title, platform, tag[num]))
            num += 1
    
    i+=1
    #l+=1

    conn.commit()
    print(f'{i}.{title} DB 입력 완료')
    driver.close()
    driver.switch_to.window(driver.window_handles[-1])

sleep(2)
print("크롤링 완료")
driver.quit()

"""
#액셀 인스턴스 생성, 경로 지정
excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active

row_column = ["게임명", "정가", "할인가", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\TendoSale\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

    #title이 자꾸 저장안돼서 생각해보니 첫글자가 공백이라 엑셀에 공백+문자열을 넣으면 입력이 생략되는거같아서
    #문자열의 앞뒤 공백을 없애주는 .strip()을 이용하여 데이터를 집어넣으니 정상적으로 title 값이 저장됨
    data_column = [title.strip(), price, saleprice, imgdata]
    excel_sheet.append(data_column)

#액셀 저장 후 종료
excel_File.save(f'{path}\TendoSale_{timestr}.xlsx')
excel_File.close()
"""