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

#'더보기' 버튼을 계속 눌러서 게임 정보를 전부 불러온후 다시 button을 find_element로 찾으려하면 None이 반환되지않고 오류가떠서 함수로 찾는것을 추가
def find_element_button(driver):
    try:
        return driver.find_element(By.CSS_SELECTOR, "#SaleSection_13268 > div.partnersaledisplay_SaleSection_2NfLq.eventbbcodeparser_SaleSectionCtn_2Xrw_.SaleSectionForCustomCSS > div.saleitembrowser_SaleItemBrowserContainer_2wLns > div:nth-child(2) > div.facetedbrowse_FacetedBrowseInnerCtn_hWbTI > div > div.saleitembrowser_ShowContentsContainer_3IRkb > button")
    except NoSuchElementException as _:
        return None

#현재시간 변수에 저장(파일경로와 파일이름에 현재시간을 넣기위해)
time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

#스팀 상점 URL 설정
URL = 'https://store.steampowered.com/specials/'

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

#현재 페이지 주소 html에 설정, BeautilfulSoup으로 페이지 parsing
html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#정보를 한글로 크롤링하기위해 페이지 언어 변경
steam_language = soup.find("span", {"class": "pulldown global_action_link"})

if steam_language.string != "언어":
    driver.find_element(By.CSS_SELECTOR, "#language_pulldown").click()
    driver.find_element(By.CSS_SELECTOR, "#language_dropdown > div > a:nth-child(4)").click()
sleep(5)



"""
# 현재 페이지 스크롤 전체 길이 추출
driver.execute_script('return document.body.scrollHeight')
'
# 현재 스크롤 좌표 추출
driver.execute_script('return window.pageYOffset')

# 지정 좌표로 스크롤 이동
driver.execute_script('window.scrollTo(x, y)')
"""

#스크롤을 최하단까지 중간에 1.5초의 텀을주고 두번 내려서 동적으로 생성되는 객체들을 불러온후 스크롤을 최하단으로 내림
#스크롤을 내리는 이유 : 할인 상품들은 동적으로 생성되서 스크롤을 내려 생성시킨후 '더 보기' 버튼 객체를 찾기위함
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(1.5)
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(1.5)

#반복문으로 할인중인 게임 목록에 '더 보기' 버튼이 존재하면 해당 버튼을 누르고 없어지면 반복문을 빠져나오게 작성
while True:
    #현재 할인중인 상품들을 펼쳐보려면 '더 보기' 버튼을 눌러야해서 button 변수에 '더 보기' 버튼의 경로를 설정
    button = find_element_button(driver)
    #if else문 : '더 보기' 버튼이 존재하면(None 타입이 아니라 WebElement 타입이 반환되면)
    if button != None:
        #화면을 '더 보기' 버튼이 있는곳으로 이동시킨 후 버튼을 클릭한다
        action.move_to_element(button).click().perform()
    else: #'더 보기' 버튼이 존재하지않으면 break로 반복문 빠져나옴
        break

#더보기 다누르면 화면 맨아래로 내려서 동적데이터 전부 생성한후 다시 페이지 파싱
sleep(1.5)
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#액셀 인스턴스 생성, 경로 지정
excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active

row_column = ["순위", "게임명", "정가", "할인가", "할인율", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\SteamSale\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

#게임 데이터 크롤링
panel = soup.select_one("div.saleitembrowser_SaleItemBrowserContainer_2wLns")
gamelist = panel.select("div.salepreviewwidgets_SaleItemBrowserRow_y9MSd")

i = 1
for item in gamelist:
    title = item.find("div", class_="salepreviewwidgets_StoreSaleWidgetTitle_3jI46 StoreSaleWidgetTitle")
    price = item.find("div", class_="salepreviewwidgets_StoreOriginalPrice_1EKGZ")
    saleprice = item.find("div", class_="salepreviewwidgets_StoreSalePriceBox_Wh0L8")
    saleper = item.find("div", class_="salepreviewwidgets_StoreSaleDiscountBox_2fpFv")
    img = item.find("img", class_="salepreviewwidgets_CapsuleImage_cODQh")
    
    #게임은 할인하는데 확장팩이 할인안하는 경우 price와 saleper가 안적혀있어서 if문 작성
    imgdata = img["src"]
    if price == None:
        price = "할인 X"
        saleper = "할인 X"
    else:
        price = price.text
        saleper = saleper.text

    #액셀에 데이터 집어넣기, 이미지 저장
    data_column = [i, title.text, price, saleprice.text, saleper, imgdata]
    excel_sheet.append(data_column)
    dload.save(imgdata, f'{path}\IMG_{i}.jpg')
    i += 1

#액셀 저장 후 종료
excel_File.save(f'{path}\SteamSale_{timestr}.xlsx')
excel_File.close()

#모든 작업이 완료되면 5초 대기했다가 인스턴스 해제
sleep(5)
driver.quit()