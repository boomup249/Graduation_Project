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

conn = MySQLdb.connect(
    user="root",
    passwd="1937",
    host="localhost",
    db="member"
)
cursor = conn.cursor()

# 모든 레코드의 varia 값을 0으로 업데이트
update_query = "UPDATE release_info SET VARIA = 0"
cursor.execute(update_query)
conn.commit()

dictionary_list = []
#1.여기서 크롤링을해서 dictionary_list로 값을 다 넣은다음에 
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
                platform = None

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
            data = {'DATE' : date, 'TITLE' : title, 'PLATFORM' : platform, 'PRICE' : price}
            dictionary_list.append(data)

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

#2.여기서 딕셔너리의 title값을 하나씩 가져와서 insert나 update문으로 db 처리
for data in dictionary_list:
    date = data['DATE']
    title = data['TITLE']
    platform = data['PLATFORM']
    price = data['PRICE']
    # 데이터베이스에 해당 TITLE이 존재하는지 확인
    query = "SELECT * FROM release_info WHERE TITLE = %s AND platform = %s"
    cursor.execute(query, (title, platform))
    result = cursor.fetchone()

    if result is None:
        # 데이터베이스에 존재하지 않으면 INSERT
        insert_query = "INSERT INTO release_info (DATE, TITLE, PLATFORM, PRICE) VALUES (%s, %s, %s, %s)"
        cursor.execute(insert_query, (date, title, platform, price))
        conn.commit()
        print(f"INSERT: {title}")
    else:
        # 데이터베이스에 이미 존재하면 price를 비교하여 업데이트하고 varia 값을 1로 변경
        existing_price = result[3]  # 결과에서 현재 데이터베이스의 price 가져오기
        if existing_price != price:
            update_query = "UPDATE release_info SET PRICE = %s, VARIA = 1 WHERE TITLE = %s AND PLATFORM = %s"
            cursor.execute(update_query, (price, title, platform))
            conn.commit()
            print(f"UPDATE: {title} (Price || Varia Updated)")
        else:
            update_query = "UPDATE release_info SET VARIA = 1 WHERE TITLE = %s AND PLATFORM = %s"
            cursor.execute(update_query, (title, platform))
            conn.commit()
            print(f"UPDATE: {title} (Price Not Changed, VARIA UPDATE)")

#varia값 변경이 전부끝났으면 0은 전부 삭제
delete_query = "DELETE FROM release_info WHERE VARIA = 0"
cursor.execute(delete_query)
conn.commit()

if conn:
    cursor.close()
    conn.close()