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

URL = 'https://store.epicgames.com/ko/collection/top-sellers'

#크롬드라이버 옵션 설정
options = Options()
options.add_experimental_option("detach", True)

#크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,1000)
driver.get(URL)
driver.implicitly_wait(10)

#ActionChains 사용하기위해 action 미리 지정
action = ActionChains(driver)

page = driver.find_element(By.TAG_NAME, "body")

for _ in range(0,20):
    page.send_keys(Keys.PAGE_DOWN)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#액셀 인스턴스 생성, 경로 지정
excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active

row_column = ["게임명", "정가","파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\EpicTopSeller\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

#게임 데이터 크롤링
panel = soup.select_one("section.css-zjpm9r")
gamelist = panel.select("div.css-lrwy1y")

i = 1

for item in gamelist:
    title = item.find("div", class_="css-rgqwpc")
    if title == None:
        title = item.find("div",class_="css-8uhtka")
    price = item.find("span", class_="css-119zqif")
    img = item.find("img", class_="css-174g26k")
    
    imgdata = img["src"]

    data_column = [i, title.text, price.text,imgdata]
    excel_sheet.append(data_column)
    dload.save(imgdata, f'{path}\IMG_{i}.jpg')
    i += 1

#액셀 저장 후 종료
excel_File.save(f'{path}\EpicTopSeller_{timestr}.xlsx')
excel_File.close()

driver.quit()