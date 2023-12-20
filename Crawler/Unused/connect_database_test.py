import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchElementException
from bs4 import BeautifulSoup
from time import sleep
import MySQLdb

#DB연결
conn = MySQLdb.connect(
    user="crawler",
    passwd="crawler1937",
    host="localhost",
    db="crawling_test"
)
cursor = conn.cursor()

# 실행할 때마다 다른값이 나오지 않게 테이블을 제거해두기
cursor.execute("DROP TABLE IF EXISTS epic_best")

#steam_best 테이블 생성
#rank에 AUTO_INCREMENT를 사용함으로써 INSERT가 입력될때마다 자동으로 숫자를 +1 올린다
cursor.execute('''CREATE TABLE epic_best (
                                           num int(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           title varchar(100) NOT NULL,
                                           price varchar(30) NOT NULL,
                                           saleprice varchar(30),
                                           saleper varchar(30),
                                           imgdata varchar(3000)
                                           )
                                           ''')

URL = 'https://store.epicgames.com/ko/collection/most-played'

options = Options()
options.add_experimental_option("detach", True)

driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,800)
driver.get(URL)
driver.implicitly_wait(10)

page = driver.find_element(By.TAG_NAME, "body")
for _ in range(0,20):
    page.send_keys(Keys.PAGE_DOWN)

sleep(3)
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(2) 

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

panel = soup.select_one("section.css-zjpm9r")
gamelist = panel.select("div.css-lrwy1y")

i = 1

for item in gamelist:
    title = item.find("div", class_="css-rgqwpc")
    if title == None:
        title = item.find("div",class_="css-8uhtka").text
    else:
        title = title.text
    price = item.find("span", class_="css-119zqif")
    if price == None:
        price = "정보 없음"
    else:
        price = price.text
    
    saleper = item.find("div", class_="css-b0xoos")
    if saleper != None:
        price = item.find("div", class_="css-4jky3p").text
        saleprice = item.find("span", class_="css-119zqif").text
        saleper = saleper.text
    else:
        saleper = "X"
        saleprice = "X"
    
    img = item.find("img", class_="css-174g26k")
    imgdata = img["src"]
    
    sql = 'INSERT INTO epic_best (title, price, saleprice, saleper, imgdata) VALUES (%s, %s, %s, %s, %s)'
    cursor.execute(sql, (title, price, saleprice, saleper, imgdata))

conn.commit()
sleep(5)
driver.quit()
conn.close()