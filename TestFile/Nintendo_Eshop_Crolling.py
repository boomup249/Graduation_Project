import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchElementException
from bs4 import BeautifulSoup
import openpyxl
from pathlib import Path
from io import BytesIO
import dload
from time import sleep
from datetime import datetime

time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

URL = 'https://store.nintendo.co.kr/games/sale'

#크롬드라이버 옵션 설정
options = Options()
options.add_experimental_option("detach", True)

#크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,1000)
driver.get(URL)
driver.implicitly_wait(10)

sleep(2)
driver.find_element(By.CLASS_NAME, 'popup-close').click()
sleep(2)
page = driver.find_element(By.TAG_NAME, "body")
for _ in range(0,85):
    page.send_keys(Keys.PAGE_DOWN)
    sleep(0.1)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#액셀 인스턴스 생성, 경로 지정
excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active

row_column = ["게임명", "정가", "할인가", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\TendoSale\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

#게임 데이터 크롤링
panel = soup.select_one("div.category-product-list")
gamelist = panel.select("div.category-product-item")

i = 1
for item in gamelist:
    title = item.find("a", class_="category-product-item-title-link").text
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

    #title이 자꾸 저장안돼서 생각해보니 첫글자가 공백이라 엑셀에 공백+문자열을 넣으면 입력이 생략되는거같아서
    #문자열의 앞뒤 공백을 없애주는 .strip()을 이용하여 데이터를 집어넣으니 정상적으로 title 값이 저장됨
    data_column = [title.strip(), price, saleprice, imgdata]
    excel_sheet.append(data_column)
    i += 1

#액셀 저장 후 종료
excel_File.save(f'{path}\TendoSale_{timestr}.xlsx')
excel_File.close()

driver.quit()
