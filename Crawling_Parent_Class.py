import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import StaleElementReferenceException
from webdriver_manager.chrome import ChromeDriverManager
from bs4 import BeautifulSoup
from time import sleep
from datetime import datetime
import MySQLdb
import re

class Crawling_Game_Info():
    #생성자 __init__
    def __init__(self):
        #크롤링이 끝났을때 시간 비교를위해 time, timestr 선언
        self.starttime = datetime.now()
        self.starttimestr = self.starttime.strftime("%Y%m%d_%H%M")
        self.dictionary_list = []
        self.dictionary_list_genre = []

    #소멸자 __del__
    def __del__(self):
        nowtime = datetime.now()
        formattime = nowtime.strftime("%Y-%m-%d %H:%M:%S")
        sql = 'UPDATE crawling_time SET ENDTIME = %s ORDER BY ENDTIME LIMIT 1'
        self.cursor.execute(sql, (formattime, ))
        self.conn.commit()
        print(f'{self.platform} 크롤링 종료 시간 - {formattime}, crawling_time 테이블에 종료 시간 입력 완료')

        if hasattr(self, 'cursor') and self.cursor:
            self.cursor.close()
        if hasattr(self, 'conn') and self.conn:
            self.conn.close()
        print("인스턴스 해제 완료")

    def Set_mysql_connect(self, password):
        self.conn = MySQLdb.connect(
                    user="root",
                    passwd=password,
                    host="localhost",
                    db="member",
                    charset='utf8'
                )
        self.cursor = self.conn.cursor()

    #URL 설정 함수
    def Set_URL_Platform(self, url, platform):
        self.URL = url
        self.platform = platform

    #css_selector 객체 찾아서 return해주는 함수(스팀 버튼찾기)
    def find_element_by_css(self, driver, css):
        try:
            return driver.find_element(By.CSS_SELECTOR, css)
        except NoSuchElementException as _:
            return None

    #마우스 스크롤 내리는 함수
    def Check_Mouse_Scroll_Down(self, driver):
        for _ in range(0, 3):
            driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
            sleep(0.3)

    #스크롤 PagwDown으로 내리는 함수
    def Check_Mouse_Scroll_Send_Keys(self, driver):
        page = driver.find_element(By.TAG_NAME, "body")
        scroll_max = driver.execute_script('return document.body.scrollHeight')
        scroll_now = 0
        for _ in range(10000):
            if scroll_max - scroll_now > 3000:
                page.send_keys(Keys.PAGE_DOWN)
                print(f"{self.platform} - SendKey:PageDown")
                sleep(0.3)
                scroll_now = driver.execute_script('return window.pageYOffset')
            else:
                for __ in range(1,16):
                    page.send_keys(Keys.PAGE_DOWN)
                    print(f"{self.platform} - SendKey:PageDown")
                    sleep(0.3)
                break

    #이모티콘 제거 함수
    def rmEmoji_ascii(inputString):
        return inputString.encode('utf-8', 'ignore').decode('utf-8')
    
    #driver 생성 함수
    def Driver_Start(self, platform, URL):
        #크롬드라이버 옵션 설정
        services = Service(executable_path=ChromeDriverManager().install())
        options = Options()
        options.add_experimental_option("detach", True)
        options.add_argument("--headless=new")
        options.add_argument("disable-gpu")
        options.add_argument("lang=ko_KR")
        options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')

        #크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
        driver = webdriver.Chrome(service=services, options=options)
        driver.implicitly_wait(30)
        driver.set_window_size(1920,1080)
        driver.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument", {"source": """ Object.defineProperty(navigator, 'webdriver', { get: () => undefined }) """})
        driver.execute_script("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},});")
        driver.execute_script("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})")
        driver.get(URL)

        print(platform,"driver 생성 완료")
        return driver
    
    #Gamedata Insert 함수
    def Insert_GameData(self):
        db_num = 1
        dictionary_list = self.dictionary_list
        self.table_name = f'gamedata_{self.platform}'

        update_query = 'UPDATE {} SET VARIA = 0'.format(self.table_name)
        self.cursor.execute(update_query)
        self.conn.commit()

        for data in dictionary_list:
            d_title = data['TITLE']
            d_price = data['PRICE']
            d_saleprice = data['SALEPRICE']
            d_saleper = data['SALEPER']
            d_description = data['DESCRIPTION']
            d_imgdata = data['IMGDATA']
            d_gameimg = data['GAMEIMG']
            d_url = data['URL']

            d_title = self.rmEmoji_ascii(d_title)
            d_description = self.rmEmoji_ascii(d_description)
            
            # 데이터베이스에 해당 NUM이 존재하는지 확인
            query = 'SELECT * FROM {} WHERE NUM = %d'.format(self.table_name)
            self.cursor.execute(query, (db_num, ))
            result = self.cursor.fetchone()

            if result is None:
                # NUM이 데이터베이스에 존재하지 않으면 INSERT(인기순으로 크롤링해오기때문에 NUM=1부터 높은순서로 INSERT)
                insert_query = '''INSERT INTO {} (NUM, TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL) 
                                          VALUES (%d, %s, %s, %s, %s, %s, %s, %s, %s)'''.format(self.table_name)
                self.cursor.execute(insert_query, (db_num, d_title, d_price, d_saleprice, d_saleper, d_description, d_imgdata, d_gameimg, d_url))
                self.conn.commit()
                print(f"{self.platform} - INSERT: {d_title}")
            else:
                # 데이터베이스에 이미 존재하면 UPDATE
                update_query = '''UPDATE {} SET TITLE = %s, PRICE = %s, SALEPRICE = %s, SALEPER = %s, DESCRIPTION = %s, 
                                                IMGDATA = %s, GAMEIMG = %s, URL = %s, VARIA = 1 WHERE NUM = %d'''.format(self.table_name)
                self.cursor.execute(update_query, (d_title, d_price, d_saleprice, d_saleper, d_description, d_imgdata, d_gameimg, d_url, db_num))
                self.conn.commit()
                print(f"{self.platform} - UPDATE: {d_title}")
            db_num += 1

        #varia값 변경이 전부끝났으면 0은 전부 삭제
        delete_query = 'DELETE FROM {} WHERE VARIA = 0'.format(self.table_name)
        self.cursor.execute(delete_query)
        self.conn.commit()

    #Genre Insert 함수
    def Insert_GenreData(self):
        dictionary_list_genre = self.dictionary_list_genre
        genre_table_name = f'gamedata_{self.platform}_genre'

        #장르는 gamedata에서 title이 지워지면 genre의 title도 다삭제돼서 없으면 insert만 하면됨
        for data in dictionary_list_genre:
            g_title = data['TITLE']
            g_genre = data['GENRE']
            
            g_title = self.rmEmoji_ascii(g_title)

            query = 'SELECT * FROM {} WHERE TITLE = %s'.format(self.table_name)
            self.cursor.execute(query, (g_title, ))
            result = self.cursor.fetchone()

            if result is None: # title값이 존재하지않으면 장르는 안넣어도됨
                continue
            else: # title값이 존재하면 장르 넣어야함
                insert_query = 'INSERT INTO {} (TITLE, GENRE) VALUES (%s, %s)'.format(genre_table_name)
                try:
                    self.cursor.execute(insert_query, (g_title, g_genre))
                except:
                    print(f"{self.platform} - {g_title} : 타이틀 중복 오류")
                self.conn.commit()
            print(f'{self.platform} - {g_title} genre 입력 완료')
        
    #데이터 읽어와서 저장하는 함수(steam은 오버라이딩 해야함)
    def Data_Crawling(self):
        self.Insert_GameData()
        self.Insert_GenreData()

        newtime = datetime.now()
        newtimestr = newtime.strftime("%Y%m%d_%H%M")

        sleep(2)
        print(self.platform, " 크롤링 및 DB 적용 완료 - 시작시간:", self.starttimestr, ", 완료시간:", newtimestr)

    #크롤링 시작 함수(오버라이딩 해야함)
    def Start_Crawling(self):
        pass