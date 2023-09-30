import multiprocessing
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
import MySQLdb
import requests
from time import sleep
from datetime import datetime

conn = MySQLdb.connect(
    user="root",
    passwd="1937",
    host="localhost",
    db="member"
)
cursor = conn.cursor()

cursor.execute("DROP TABLE IF EXISTS release_info")
cursor.execute('''CREATE TABLE IF NOT EXISTS release_info (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `DATE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PLATFORM` VARCHAR(15) NULL DEFAULT NULL,
                                                            `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            PRIMARY KEY (`NUM`)
            )''')

url = 'https://prod.danawa.com/game/index.php'

services = Service(executable_path=ChromeDriverManager().install())

options = Options()
options.add_experimental_option("detach", True)
options.add_argument("headless")
options.add_argument("disable-gpu")
options.add_argument("lang=ko_KR")
options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')

driver = webdriver.Chrome(options=options)
driver.implicitly_wait(10)
driver.set_window_size(1920,1080)
driver.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument", {"source": """ Object.defineProperty(navigator, 'webdriver', { get: () => undefined }) """})
driver.execute_script("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},});")
driver.execute_script("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})")
driver.get(url)
action = ActionChains(driver)


before_date = ""
dict_list = []

for _ in range(10000000000000):
    soup = BeautifulSoup(driver.page_source, "html.parser")
    gamelist = soup.select_one('div.upc_tbl_wrap').select('tr')

    for list in gamelist:
        date = list.select_one('td.date')
        if date != None:
            date = date.text.strip()

        if date == "":
            date = before_date

        platform = list.select_one('td.type')

        if platform != None:
            platform = platform.text.strip()
            if "XBOX" in platform:
                platform == None

        title = list.select_one('a.tit_link')
        if title != None:
            if date == None:
                date = before_date
            title = title.text.strip()

        price = list.select_one('em.num')

        if price == None:
            price = list.select_one('span.p_txt')

        if price != None:
            price = price.text.strip()
        
        before_date = date
        if date != None and platform != None and title != None and price != None:
            print("요일 :", date,", 플랫폼 :", platform, ", 제목 :", title, ", 가격 :", price)

        if title != None:
            sql = 'INSERT INTO release_info (DATE, TITLE, PLATFORM, PRICE) VALUES (%s, %s, %s, %s)'
            cursor.execute(sql, (date, title, platform, price))
            conn.commit()
            print(f'{title} DB 입력 완료')

    next_page_btn = driver.find_element(By.XPATH, '//*[@id="#nav_edge_next"]')
    action.click(next_page_btn).perform()

    sleep(3)

    new_soup = BeautifulSoup(driver.page_source, "html.parser")
    x = new_soup.select_one('p.n_txt')
    if x != None:
        break

sleep(2)
print("크롤링 완료")
driver.quit()