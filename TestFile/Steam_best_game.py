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
from time import sleep
#from datetime import datetime
#import openpyxl
#from io import BytesIO
#import dload
#from pathlib import Path
import MySQLdb
import requests

def find_element_by_css(driver, css_selector):
    try:
        return driver.find_element(By.CSS_SELECTOR, css_selector)
    except NoSuchElementException as _:
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
cursor.execute("DROP TABLE IF EXISTS steam_best_popluar_genre")
cursor.execute("DROP TABLE IF EXISTS steam_best_popluar")

#steam_best 테이블 생성
#rank에 AUTO_INCREMENT를 사용함으로써 INSERT가 입력될때마다 자동으로 숫자를 +1 올린다
cursor.execute('''CREATE TABLE steam_best_popluar (
                                           num int(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           title varchar(100) NOT NULL UNIQUE KEY,
                                           price varchar(30) NOT NULL,
                                           saleprice varchar(30),
                                           saleper varchar(30),
                                           description varchar(500),
                                           imgdata varchar(3000),
                                           gameimg varchar(3000)
                                           )
                                           ''')

cursor.execute('''CREATE TABLE steam_best_popluar_genre (
                                           num INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                           title varchar(100) NOT NULL,
                                           genre varchar(20) NOT NULL,
                                           FOREIGN KEY(title) REFERENCES steam_best_popluar(title)
                                           )
                                           ''')

URL = 'https://store.steampowered.com/charts/topselling'

services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_experimental_option("detach", True)

driver = webdriver.Chrome(service=services, options=options)
driver.set_window_size(1400,800)
driver.get(URL)
driver.implicitly_wait(5)

action = ActionChains(driver)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

steam_language = soup.find("span", {"class": "pulldown global_action_link"})

if steam_language.get_text() != "언어":
    driver.find_element(By.XPATH, '//*[@id="language_pulldown"]').click()
    driver.find_element(By.XPATH, '//*[@id="language_dropdown"]/div/a[4]').click()

sleep(1.5)
steam_login = soup.select_one('a.global_action_link')

if steam_login.text == "login":
    driver.find_element(By.XPATH, '//*[@id="global_action_menu"]/a[2]').click()
    driver.find_element(By.XPATH, '//*[@id="responsive_page_template_content"]/div[3]/div[1]/div/div/div/div[2]/div/form/div[1]/input').send_keys('yuhanloco')
    driver.find_element(By.XPATH, '//*[@id="responsive_page_template_content"]/div[3]/div[1]/div/div/div/div[2]/div/form/div[2]/input').send_keys('yuhan1234')
    driver.find_element(By.XPATH, '//*[@id="responsive_page_template_content"]/div[3]/div[1]/div/div/div/div[2]/div/form/div[4]/button').click()

sleep(5)
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(2) 

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

panel = soup.find('div', attrs={'data-featuretarget': 'react-root'})
gamelist = panel.select('tr.weeklytopsellers_TableRow_2-RN6')

mode = 0
for item in gamelist:
    title = item.select_one('div.weeklytopsellers_GameName_1n_4-').text
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
        #if문으로 saleprice(현재 판매가) 값이 "무료 플레이"가 아니면 price(정가)는 현재 판매가랑같은 값을 갖게됨(할인을 안하는중이라)
        if saleprice == None:
            price = "X"
            saleper = "X"
            saleprice = "X"
        elif saleprice != "무료 플레이":
            price = saleprice.text
            saleper = "X"
            saleprice = "X"
        #else문으로 saleprice값이 "무료 플레이"면 정가와 할인율을 무료로 변경
        else:
            price = "무료"
            saleper = "무료"
            saleprice = "무료"
    #정가가 적혀있으면 정가와 할인율이 둘다 문자열로 존재할테니 price, saleper 변수를 문자열 자료형으로 변환
    else:
        price = price.text
        saleper = saleper.text
        saleprice = saleprice.text

    #move 변수에 해당 게임 페이지 링크 획득
    game_link = item.select_one('a.weeklytopsellers_TopChartItem_2C5PJ')
    move = game_link["href"]
    response = requests.get(move, cookies={'Steam_Language':'koreana'}).text

    """
    #게임 페이지로 새탭에서 열기
    driver.execute_script(f'window.open(\'{move}\');')
    #새탭으로 이동
    driver.switch_to.window(driver.window_handles[-1])
    sleep(1)

    #해당탭 크롤링
    new_html = driver.page_source
    """
    new_soup = BeautifulSoup(response, "html.parser")

    """
    check = new_soup.select_one("div.agegate_btn_ctn")
    if check != None:
        driver.find_element(By.XPATH, '//*[@id="ageYear"]').click()
        driver.find_element(By.XPATH, '//*[@id="ageYear"]/option[88]').click()
        driver.find_element(By.XPATH, '//*[@id="view_product_page_btn"]/span').click()
        sleep(1)
        new_html = driver.page_source
        new_soup = BeautifulSoup(new_html, "html.parser")
        """

    gameimg = new_soup.select_one('img.game_header_image_full')

    if gameimg == None:
        mode = 1
        driver.execute_script(f'window.open(\'{move}\');')
        driver.switch_to.window(driver.window_handles[-1])
        if find_element_by_css(driver, '#ageYear') != None:
            driver.find_element(By.XPATH, '//*[@id="ageYear"]').click()
            driver.find_element(By.XPATH, '//*[@id="ageYear"]/option[88]').click()
            driver.find_element(By.XPATH, '//*[@id="view_product_page_btn"]/span').click()
            sleep(3)
        new_html = driver.page_source
        new_soup = BeautifulSoup(new_html, "html.parser")
        gameimg = new_soup.select_one('img.game_header_image_full') 
        if gameimg == None:
            gameimg = new_soup.select_one('img.package_header')

    if gameimg != None:
        gameimg = gameimg["src"]

    description = new_soup.select_one("div.game_description_snippet")
    if description != None:
        description = description.text.strip()
    tag = new_soup.select("a.app_tag")
    
    tag_length = len(tag)
    num = 0

    sql = 'INSERT INTO steam_best_popluar (title, price, saleprice, saleper, description, imgdata, gameimg) VALUES (%s, %s, %s, %s, %s, %s, %s)'
    cursor.execute(sql, (title, price, saleprice, saleper, description, imgdata, gameimg))

    while num < tag_length:
        tag[num] = tag[num].text.strip()
        sql = 'INSERT INTO steam_best_popluar_genre (title, genre) VALUES (%s, %s)'
        cursor.execute(sql, (title, tag[num]))
        num += 1

    conn.commit()
    print(f'{title} DB 입력 완료')

    #탭 종료후 원래 탭(베스트게임 페이지)으로 이동
    if mode == 1:
        driver.close()
        driver.switch_to.window(driver.window_handles[-1])
        mode = 0

sleep(2)
print("크롤링 완료")
driver.quit()


#excel_File = openpyxl.Workbook()
#excel_sheet = excel_File.active

#row_column = ["순위", "게임명", "할인가", "정가", "할인율", "파일 이미지"]
#excel_sheet.append(row_column)

#path = f'D:\Python\Study\SteamBest\{timestr}'
#Path(path).mkdir(parents=True, exist_ok=True)

"""
i = 1
for item in gamelist:
    title = item.select_one('div.weeklytopsellers_GameName_1n_4-').text
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
        #if문으로 saleprice(현재 판매가) 값이 "무료 플레이"가 아니면 price(정가)는 현재 판매가랑같은 값을 갖게됨(할인을 안하는중이라)
        if saleprice != "무료 플레이":
            price = saleprice.text
            saleper = "X"
            saleprice = "X"
        #else문으로 saleprice값이 "무료 플레이"면 정가와 할인율을 무료로 변경
        else:
            price = "무료"
            saleper = "무료"
            saleprice = "무료"
    #정가가 적혀있으면 정가와 할인율이 둘다 문자열로 존재할테니 price, saleper 변수를 문자열 자료형으로 변환
    else:
        price = price.text
        saleper = saleper.text
        saleprice = saleprice.text

    print(f'{i}.{title}, 정가:{price}, 할인가:{saleprice}, 할인율:{saleper}, 이미지링크:{imgdata}')
    #excel_sheet.append(data_column)
    #dload.save(imgdata, f'{path}\IMG_{i}.jpg')
    i += 1
#excel_File.save(f'{path}\CrollingTest_{timestr}.xlsx')
#excel_File.close()


sleep(5)
driver.quit()
"""