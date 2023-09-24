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
import requests

#'더보기' 버튼을 계속 눌러서 게임 정보를 전부 불러온후 다시 button을 find_element로 찾으려하면 None이 반환되지않고 오류가떠서 함수로 찾는것을 추가
def find_element_by_css(driver, css_selector):
    try:
        return driver.find_element(By.CSS_SELECTOR, css_selector)
    except NoSuchElementException as _:
        return None

time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

#DB연결
conn = MySQLdb.connect(
    user="root",
    passwd="1937",
    host="localhost",
    db="member"
)
cursor = conn.cursor()

# 실행할 때마다 다른값이 나오지 않게 테이블을 제거해두기
cursor.execute("DROP TABLE IF EXISTS gamedata_steam_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_steam")

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_steam (`NUM` INT NOT NULL AUTO_INCREMENT,
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

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_steam_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                   `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                   `genre` VARCHAR(30) NULL DEFAULT NULL,
                                                                   PRIMARY KEY (`NUM`),
                                                                   CONSTRAINT `steam_title`
                                                                       FOREIGN KEY (`TITLE`)
                                                                       REFERENCES `gamedata_steam` (`TITLE`)
                                                                       ON DELETE CASCADE
                                                                       ON UPDATE CASCADE)
               ''')

#스팀 상점 URL 설정
URL = 'https://store.steampowered.com/specials/'

#크롬드라이버 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())

options = Options()
options.add_experimental_option("detach", True)
#options.add_argument("headless")
options.add_argument("disable-gpu")
options.add_argument("disable-infobars")
options.add_argument("--disable-extensions")
options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')

driver = webdriver.Chrome(service=services, options=options)
driver.implicitly_wait(5)
driver.set_window_size(1400,1000)
driver.get(URL)

#ActionChains 사용하기위해 action 미리 지정
action = ActionChains(driver)

#현재 페이지 주소 html에 설정, BeautilfulSoup으로 페이지 parsing
html = driver.page_source
soup = BeautifulSoup(html, 'html.parser')

#정보를 한글로 크롤링하기위해 페이지 언어 변경
steam_language = soup.find("span", {"class": "pulldown global_action_link"})

if steam_language.string != "언어":
    driver.find_element(By.CSS_SELECTOR, "#language_pulldown").click()
    driver.find_element(By.CSS_SELECTOR, "#language_dropdown > div > a:nth-child(4)").click()
    print("언어설정 완료")

sleep(1.5)

steam_login = soup.select_one('a.global_action_link')

if steam_login.text == "login":
    driver.find_element(By.XPATH, '//*[@id="global_action_menu"]/a[2]').click()
    driver.find_element(By.XPATH, '//*[@id="responsive_page_template_content"]/div[3]/div[1]/div/div/div/div[2]/div/form/div[1]/input').send_keys('yuhanloco')
    driver.find_element(By.XPATH, '//*[@id="responsive_page_template_content"]/div[3]/div[1]/div/div/div/div[2]/div/form/div[2]/input').send_keys('yuhan1234')
    driver.find_element(By.XPATH, '//*[@id="responsive_page_template_content"]/div[3]/div[1]/div/div/div/div[2]/div/form/div[4]/button').click()
    print("로그인 완료")

sleep(2)

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
driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
sleep(1.5)
driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
sleep(1.5)

btn_num = 1
#반복문으로 할인중인 게임 목록에 '더 보기' 버튼이 존재하면 해당 버튼을 누르고 없어지면 반복문을 빠져나오게 작성
for _ in range(10000000000):
    if btn_num < 30:
        #현재 할인중인 상품들을 펼쳐보려면 '더 보기' 버튼을 눌러야해서 button 변수에 '더 보기' 버튼의 경로를 설정
        button = find_element_by_css(driver, '#SaleSection_13268 > div.partnersaledisplay_SaleSection_2NfLq.eventbbcodeparser_SaleSectionCtn_2Xrw_.SaleSectionForCustomCSS > div.saleitembrowser_SaleItemBrowserContainer_2wLns > div:nth-child(2) > div.facetedbrowse_FacetedBrowseInnerCtn_hWbTI > div > div.saleitembrowser_ShowContentsContainer_3IRkb > button')
        #if else문 : '더 보기' 버튼이 존재하면(None 타입이 아니라 WebElement 타입이 반환되면)
        if button != None:
            #화면을 '더 보기' 버튼이 있는곳으로 이동시킨 후 버튼을 클릭한다
            action.move_to_element(button).click().perform()
            print(btn_num, "버튼 클릭")
            btn_num += 1
        else: #'더 보기' 버튼이 존재하지않으면 break로 반복문 빠져나옴
            print("버튼 전부 클릭 완료 - ", btn_num, "번 실행")
            break
    else:
        btn_url = driver.current_url
        driver.quit()
        driver = webdriver.Chrome(options=options)
        driver.implicitly_wait(5)
        driver.set_window_size(1400,1000)
        driver.get(btn_url)
        btn_num = 0
        sleep(1.5)
        break

#더보기 다누르면 화면 맨아래로 내려서 동적데이터 전부 생성한후 다시 페이지 파싱
sleep(1.5)
driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#게임 데이터 크롤링
panel = soup.select_one("div.saleitembrowser_SaleItemBrowserContainer_2wLns")
gamelist = panel.select("div.salepreviewwidgets_SaleItemBrowserRow_y9MSd")

i = 1

for item in gamelist:
    title = item.find("div", class_="salepreviewwidgets_StoreSaleWidgetTitle_3jI46 StoreSaleWidgetTitle").text
    price = item.find("div", class_="salepreviewwidgets_StoreOriginalPrice_1EKGZ")
    saleprice = item.find("div", class_="salepreviewwidgets_StoreSalePriceBox_Wh0L8")
    saleper = item.find("div", class_="salepreviewwidgets_StoreSaleDiscountBox_2fpFv")
    
    game_link = item.select_one('div.salepreviewwidgets_StoreSaleWidgetHalfLeft_2Va3O > a')
    move = game_link["href"]
    
    #게임은 할인하는데 확장팩이 할인안하는 경우 price와 saleper가 안적혀있어서 if문 작성
    if price == None:
        price = "할인 X"
        saleprice = "할인 X"
        saleper = "할인 X"
    else:
        price = price.text
        saleprice = saleprice.text
        saleper = saleper.text

    driver.execute_script(f'window.open(\'{move}\');')
    driver.switch_to.window(driver.window_handles[-1])
    sleep(1.5)
    new_soup = BeautifulSoup(driver.page_source, "html.parser")

    check = new_soup.select_one("div.agegate_btn_ctn")
    if check != None:
        driver.find_element(By.XPATH, '//*[@id="ageYear"]').click()
        driver.find_element(By.XPATH, '//*[@id="ageYear"]/option[88]').click()
        driver.find_element(By.XPATH, '//*[@id="view_product_page_btn"]/span').click()
        sleep(1.5)
        new_soup = BeautifulSoup(driver.page_source, "html.parser")
        sleep(1.5)

    imgdata = new_soup.select_one('img.game_header_image_full')

    if imgdata == None:
        mode = 1
        driver.execute_script(f'window.open(\'{move}\');')
        driver.switch_to.window(driver.window_handles[-1])
        if find_element_by_css(driver, '#ageYear') != None:
            driver.find_element(By.XPATH, '//*[@id="ageYear"]').click()
            driver.find_element(By.XPATH, '//*[@id="ageYear"]/option[88]').click()
            driver.find_element(By.XPATH, '//*[@id="view_product_page_btn"]/span').click()
            sleep(1.5)
        new_html = driver.page_source
        new_soup = BeautifulSoup(new_html, "html.parser")
        sleep(1.5)
        imgdata = new_soup.select_one('img.game_header_image_full') 
        if imgdata == None:
            imgdata = new_soup.select_one('img.package_header')

    if imgdata != None:
        imgdata = imgdata["src"]

    gameimg = new_soup.select_one('a.highlight_screenshot_link')

    if gameimg != None:
        gameimg = gameimg['href']

    description = new_soup.select_one("div.game_description_snippet")
    if description != None:
        description = description.text.strip()
    tag = new_soup.select("a.app_tag")
    
    tag_length = len(tag)
    num = 0

    sql = 'INSERT INTO gamedata_steam (title, price, saleprice, saleper, description, imgdata, gameimg, url) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
    cursor.execute(sql, (title, price, saleprice, saleper, description, imgdata, gameimg, move))

    while num < tag_length:
        tag[num] = tag[num].text.strip()
        sql = 'INSERT INTO gamedata_steam_genre (title, genre) VALUES (%s, %s)'
        cursor.execute(sql, (title, tag[num]))
        num += 1

    conn.commit()
    print(f'{title} DB 입력 완료')

    #탭 종료후 원래 탭(베스트게임 페이지)으로 이동
    driver.close()
    driver.switch_to.window(driver.window_handles[-1])

time_complete = datetime.now()
timestr_complete = time_complete.strftime("%Y%m%d_%H%M")

sleep(2)
print("크롤링 완료, 시작시간 : ", timestr, ", 완료시간 : ", timestr_complete)
driver.quit()

#모든 작업이 완료되면 5초 대기했다가 인스턴스 해제
sleep(5)
driver.quit()