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
import MySQLdb
import requests

#find_element None타입 오류떠도 안멈추게 해주는 함수
def find_element_by_css(driver, css_selector):
    try:
        return driver.find_element(By.CSS_SELECTOR, css_selector)
    except NoSuchElementException as _:
        return None
    
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

#driver 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_experimental_option("detach", True)

#Steam 최고 인기 게임 크롤링
URL = 'https://store.steampowered.com/charts/topselling'
platform = "steam"

driver = webdriver.Chrome(service=services, options=options)
driver.set_window_size(1400,800)
driver.get(URL)
driver.implicitly_wait(5)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#스팀 언어설정
steam_language = soup.find("span", {"class": "pulldown global_action_link"})
if steam_language.get_text() != "언어":
    driver.find_element(By.XPATH, '//*[@id="language_pulldown"]').click()
    driver.find_element(By.XPATH, '//*[@id="language_dropdown"]/div/a[4]').click()
    sleep(5)

#스팀 로그인
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
    title = item.select_one('div.weeklytopsellers_GameName_1n_4-').text.strip('🥹')
    saleprice = item.select_one('div.salepreviewwidgets_StoreSalePriceBox_Wh0L8')
    price = item.select_one('div.salepreviewwidgets_StoreOriginalPrice_1EKGZ')
    saleper = item.select_one('div.salepreviewwidgets_StoreSaleDiscountBox_2fpFv')

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
    new_soup = BeautifulSoup(response, "html.parser")
    imgdata = new_soup.select_one('img.game_header_image_full')

    if imgdata == None:
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
        imgdata = new_soup.select_one('img.game_header_image_full') 
        if imgdata == None:
            imgdata = new_soup.select_one('img.package_header')

    if imgdata != None:
        imgdata = imgdata["src"]

    gameimg = new_soup.select_one('div.screenshot_holder')
    if gameimg != None:
        gameimg = gameimg.select_one('a')["href"]

    description = new_soup.select_one("div.game_description_snippet")
    if description != None:
        description = description.text.strip()
    tag = new_soup.select("a.app_tag")
    
    tag_length = len(tag)
    num = 0

    sql = 'INSERT INTO gamedata_info (TITLE, PLATFORM, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL) VALUES (%s, %s, %s, %s, %s, %s, %s, %s ,%s)'
    cursor.execute(sql, (title, platform, price, saleprice, saleper, description, imgdata, gameimg, move))

    while num < tag_length:
        tag[num] = tag[num].text.strip()
        sql = 'INSERT INTO gamedata_genre (TITLE, PLATFORM, GENRE) VALUES (%s, %s, %s)'
        cursor.execute(sql, (title, platform, tag[num]))
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