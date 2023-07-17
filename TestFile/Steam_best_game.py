import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from bs4 import BeautifulSoup
from time import sleep
from datetime import datetime
import openpyxl
from io import BytesIO
import dload
from pathlib import Path

time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

URL = 'https://store.steampowered.com/charts/topselling'

options = Options()
options.add_experimental_option("detach", True)

driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,800)
driver.get(URL)
driver.implicitly_wait(10)

action = ActionChains(driver)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

steam_language = soup.find("span", {"class": "pulldown global_action_link"})

if steam_language.get_text() != "언어":
    driver.find_element(By.XPATH, '//*[@id="language_pulldown"]').click()
    driver.find_element(By.XPATH, '//*[@id="language_dropdown"]/div/a[4]').click()

sleep(3)
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(2) 

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

panel = soup.find('div', attrs={'data-featuretarget': 'react-root'})
gamelist = panel.select('tr.weeklytopsellers_TableRow_2-RN6')

excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active

row_column = ["순위", "게임명", "할인가", "정가", "할인율", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\SteamBest\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)


i = 1
for item in gamelist:
    title = item.select_one('div.weeklytopsellers_GameName_1n_4-')
    saleprice = item.select_one('div.salepreviewwidgets_StoreSalePriceBox_Wh0L8')
    price = item.select_one('div.salepreviewwidgets_StoreOriginalPrice_1EKGZ')
    saleper = item.select_one('div.salepreviewwidgets_StoreSaleDiscountBox_2fpFv')
    img = item.select_one('img.weeklytopsellers_CapsuleArt_2dODJ')

    #이미지 태그가 존재하면 imgdata에 img 링크 집어넣음
    if img != None:
        imgdata = img["src"]
    #이미지 태그가 존재하지않으면 imgdata에 다른 임시 이미지 집어넣음
    else:
        imgdata = "https://i.namu.wiki/i/j_EaOmOmU8QGpxXHpZGA75dSasZthOT5_X_nlFjUO3VwaxHuf0f5_0h0yKM8I65d9Jxwe74ynwTZRLfjSL8Yk0QCt871C6A-H-76SqzQ47QO2zGVf-6MJCITrIlds4vpPaG1fmZU3Ppyv12nf803tg.webp"

    #정가가 None이면 if문 돌림
    if price == None:
        #saleprice 변수엔 "무료 플레이" 또는 가격 두가지 값이 들어갈수있음
        #if문으로 saleprice(현재 판매가) 값이 "무료 플레이"가 아니면 price(정가)는 현재 판매가랑 같은 값을 갖게됨
        if saleprice != "무료 플레이":
            price = saleprice.text
            saleper = "X"
        #else문으로 saleprice값이 "무료 플레이"면 정가와 할인율을 무료로 변경
        else:
            price = "무료"
            saleper = "무료"
    #정가가 적혀있으면 정가와 할인율이 둘다 문자열로 존재할테니 price, saleper 변수를 문자열 자료형으로 변환
    else:
        price = price.text
        saleper = saleper.text

    data_column = [i, title.text, saleprice.text, price, saleper, imgdata]
    excel_sheet.append(data_column)
    dload.save(imgdata, f'{path}\IMG_{i}.jpg')
    i += 1
excel_File.save(f'{path}\CrollingTest_{timestr}.xlsx')
excel_File.close()

sleep(5)
driver.quit()