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

#DB연결
conn = MySQLdb.connect(
    user="crawler",
    passwd="crawler1937",
    host="localhost",
    db="crawling_test"
)
cursor = conn.cursor()

# 실행할 때마다 다른값이 나오지 않게 테이블을 제거해두기
cursor.execute("DROP TABLE IF EXISTS nintendo_sale_genre")
cursor.execute("DROP TABLE IF EXISTS nintendo_sale")

#rank에 AUTO_INCREMENT를 사용함으로써 INSERT가 입력될때마다 자동으로 숫자를 +1 올린다
cursor.execute('''CREATE TABLE nintendo_sale (
                                           num int(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           title varchar(300) NOT NULL UNIQUE KEY,
                                           price varchar(30) NOT NULL,
                                           saleprice varchar(30),
                                           description varchar(2000),
                                           imgdata varchar(3000)
                                           )
                                           ''')

cursor.execute('''CREATE TABLE nintendo_sale_genre (
                                           num INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                           title varchar(100) NOT NULL,
                                           genre varchar(20) NOT NULL,
                                           FOREIGN KEY(title) REFERENCES nintendo_sale(title)
                                           )
                                           ''')

URL = 'https://store.nintendo.co.kr/games/sale'

#크롬드라이버 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_experimental_option("detach", True)

driver = webdriver.Chrome(service=services, options=options)
driver.implicitly_wait(5)
driver.set_window_size(1400,800)
driver.get(URL)

sleep(2)
driver.find_element(By.CLASS_NAME, 'popup-close').click()
sleep(2)
page = driver.find_element(By.TAG_NAME, "body")
for _ in range(0,85):
    page.send_keys(Keys.PAGE_DOWN)
    sleep(0.1)

soup = BeautifulSoup(driver.page_source, "html.parser")

#게임 데이터 크롤링
panel = soup.select_one("div.category-product-list")
gamelist = panel.select("div.category-product-item")

i=0
l=0
for item in gamelist:
    title = item.find("a", class_="category-product-item-title-link").text.strip()
    price = item.find("span", attrs={"data-price-type" : "finalPrice"})
    saleprice = item.find("span", attrs={"data-price-type" : "oldPrice"})
    img = item.find("img", class_="product-image-photo mplazyload mplazyload-transparent entered loaded")
    imgdata = img["src"]

    if price == None:
        price = item.find("span", class_="point-icon-wrapper")
        price = price.text + "포인트"
        saleprice = "포인트로 구매 가능"
    else:
        price = price.find("span").text
        saleprice = saleprice.find("span").text

    game_link = item.select_one('a')
    move = game_link["href"]
    
    if l > 120:
        print("5분간 일시정지(403 forbidden 오류 회피를 위해)")
        sleep(300)
        print("실행")
        l=0

    response = requests.get(move, headers={"User-Agent": "Mozilla/5.0"})

    if response.status_code != 200:
        print("오류 발생(403 forbidden)")
    new_soup = BeautifulSoup(response.text, "html.parser")

    description = new_soup.select_one("div.game_ex")
    if description == None:
        description = new_soup.select_one("div.value")
        description = description.text.strip()
    else:
        description = description.text.strip()

    sql = 'INSERT INTO nintendo_sale (title, price, saleprice, description, imgdata) VALUES (%s, %s, %s, %s, %s)'
    cursor.execute(sql, (title, price, saleprice, description, imgdata))

    tagparent = new_soup.find("div", class_="product-attribute game_category")
    if tagparent != None:
        tag = tagparent.find("div", class_="product-attribute-val").text.split(',')

        tag_length = len(tag)
        num = 0

        while num < tag_length:
            tag[num] = tag[num].strip()
            sql = 'INSERT INTO nintendo_sale_genre (title, genre) VALUES (%s, %s)'
            cursor.execute(sql, (title, tag[num]))
            num += 1
    
    i+=1
    l+=1

    conn.commit()
    print(f'{i}.{title} DB 입력 완료')

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