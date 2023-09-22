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
#from datetime import datetime
import MySQLdb
import requests

#DB연결
conn = MySQLdb.connect(
    user="root",
    passwd="1937",
    host="localhost",
    db="member"
)
cursor = conn.cursor()

#실행할 때마다 다른값이 나오지 않게 테이블을 제거해두기
cursor.execute("DROP TABLE IF EXISTS gamedata_ps_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_ps")

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_ps (`NUM` INT NOT NULL AUTO_INCREMENT,
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

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_ps_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                `genre` VARCHAR(30) NULL DEFAULT NULL,
                                                                PRIMARY KEY (`NUM`),
                                                                CONSTRAINT `ps_title`
                                                                    FOREIGN KEY (`TITLE`)
                                                                    REFERENCES `gamedata_ps` (`TITLE`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE)
               ''')

URL = 'https://store.playstation.com/ko-kr/pages/deals'
gameURL = 'https://store.playstation.com/'

#크롬드라이버 옵션 설정
services = Service(executable_path=ChromeDriverManager().install())
options = Options()
options.add_experimental_option("detach", True)
options.add_argument("headless")
options.add_argument("disable-gpu")
options.add_argument("lang=ko_KR")
options.add_argument('window-size=1920x1080')
options.add_argument('user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')

#크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(service=services, options=options)
driver.implicitly_wait(10)
#driver.set_window_size(1400,800)
driver.execute_cdp_cmd("Page.addScriptToEvaluateOnNewDocument", {"source": """ Object.defineProperty(navigator, 'webdriver', { get: () => undefined }) """})
driver.execute_script("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},});")
driver.execute_script("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})")
driver.get(URL)

#할인 페이지로 접속(할인페이지 url을보니 변경될수도 있을거같아서 이렇게 접속)
driver.find_element(By.XPATH, "//*[@id='main']/div/div[3]/section/div/ul/li[3]/a").click()
sleep(2)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#할인 페이지 들어오자마자 페이지바 읽어와서 마지막 페이지 값 저장
pagebar = driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')

#다음페이지 초기값(숫자)
next_page = 2
#마지막페이지 값(문자열)
last_page = pages[4].text
#마지막 게임 이름 초기값(마지막페이지에 game_list가 한개 존재할경우 그 한개를 반복해서 저장하길래 그런상황에서 break를 걸기위해 변수 하나 생성)
last_game_title = ""
#db에 넣을때 title이 중복되면 오류나서 중복되는 게임은 스킵하기위해 beforetitle 선언
beforetitle = ""

restart = 1
passnum = 0
skip_num = 0

for roof in range(1000000):
    if(restart > 3):
        new_url = driver.current_url
        driver.quit()
        driver = webdriver.Chrome(options=options)
        driver.implicitly_wait(10)
        #driver.set_window_size(1400,800)
        driver.execute_script("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},});")
        driver.execute_script("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})")
        driver.get(new_url)
        print("셀레니움 재실행(렉방지)")
        sleep(3)
        restart = 1

    #페이지가 변경됐을때 변경된 페이지를 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, "html.parser")

    sleep(1.5)

    panel = soup.find("div", class_="psw-l-w-1/1")
    game_list = panel.find_all("li", class_="psw-l-w-1/2@mobile-s psw-l-w-1/2@mobile-l psw-l-w-1/6@tablet-l psw-l-w-1/4@tablet-s psw-l-w-1/6@laptop psw-l-w-1/8@desktop psw-l-w-1/8@max")

    for game in game_list:
        try:
            title = game.find("span", class_="psw-t-body psw-c-t-1 psw-t-truncate-2 psw-m-b-2").text
        except:
            _ = 0
            while(title == None):
                if _ > 5:
                    print("title 크롤링 오류 재실행할것(트래픽이 느린것으로 추정)")
                    driver.quit()
                    break
                sleep(3)
                title = game.find("span", class_="psw-t-body psw-c-t-1 psw-t-truncate-2 psw-m-b-2").text
                _  += 1

        if title == beforetitle:
            continue
        #last_game_title의 값과 현재 크롤링하는 게임의 title의 값이 같으면 크롤링 종료
        if last_game_title == title:
            break

        img = game.find("img", class_="psw-fade-in psw-top-left psw-l-fit-cover")

        try:
            imgdata = img["src"]
        except:
            _ = 0
            while(imgdata == None):
                if _ > 5:
                    print("imgdata 크롤링 오류 재실행할것(트래픽이 느린것으로 추정)")
                    driver.quit()
                    break
                sleep(3)
                imgdata = game.find("img", class_="psw-fade-in psw-top-left psw-l-fit-cover")["src"]
                _  += 1
        
        saleper = game.find("span", class_="psw-body-2 psw-badge__text psw-badge--none psw-text-bold psw-p-y-0 psw-p-2 psw-r-1 psw-l-anchor")

        #64라인 saleper가 None이면 할인율은 무조건 -100%기때문에 price, saleprice = 무료
        if saleper == None:
            saleper = game.find("span", class_="psw-body-2 psw-badge__text psw-badge--ps-plus psw-c-bg-ps-plus psw-text-bold psw-p-y-0 psw-p-2 psw-r-1 psw-l-anchor")
            price = "무료"
            saleprice = "무료"
            #-100% 할인도 적혀있지않다면 구매할수없는 게임이기에 정가, 할인가, 할인율의 값을 "구매할 수 없음"으로 설정
            if saleper == None:
                price = game.find("span", class_="psw-m-r-3").text
                saleprice = "구매할 수 없음"
                saleper = "구매할 수 없음"
            else:
                saleper = saleper.text
        #saleper가 None이 아니였기때문에 price와 saleprice를 크롤링
        else:
            price = game.find("s").text
            saleprice = game.find("span", class_="psw-m-r-3").text
            saleper = saleper.text

        #현재 페이지와 마지막 페이지의 숫자가 똑같다면 last_game_title에 방금 크롤링한 게임 이름을 저장함
        if next_page-1 == int(last_page):
            last_game_title = title

        game_link = game.select_one('a')["href"]
        move = gameURL + game_link

        #response = requests.get(move, headers={"User-Agent": "Mozilla/5.0"})
        #new_soup = BeautifulSoup(response.text, "html.parser")

        driver.execute_script(f'window.open(\'{move}\');')
        driver.switch_to.window(driver.window_handles[-1])
        new_soup = BeautifulSoup(driver.page_source, "html.parser")
        sleep(1.5)

        description = new_soup.find('p', attrs={'data-qa': 'mfe-game-overview#description'})

        if description != None:
            description = description.text

        try:
            gameimg = new_soup.find('img', attrs={'data-qa': 'gameBackgroundImage#heroImage#image'})
            if gameimg == None:
                gameimg = new_soup.find('img', attrs={'data-qa': 'gameBackgroundImage#tileImage#image'})
        except TypeError as e:
            print(e)
            _ = 0
            while(gameimg==None):
                if _ > 5:
                    print("gameimg 크롤링 오류 재실행할것(트래픽이 느린것으로 추정)")
                    driver.quit()
                    break
                sleep(3)
                print("gameimg 추출중")
                gameimg = new_soup.find('img', attrs={'data-qa': 'gameBackgroundImage#heroImage#image'})
                if gameimg == None:
                    gameimg = new_soup.find('img', attrs={'data-qa': 'gameBackgroundImage#tileImage#image'})
                _ += 1
        except:
            gameimg = None

        if gameimg != None:
            gameimg = gameimg["src"]
        
        
        try:
            sql = 'INSERT INTO gamedata_info (TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
            cursor.execute(sql, (title, price, saleprice, saleper, description, imgdata, gameimg, move))
            
            tagparent = new_soup.find("dd", {'data-qa':'gameInfo#releaseInformation#genre-value'})
            if tagparent != None:
                tag = tagparent.select_one("span").text.split(',')
                tag_length = len(tag)
                num = 0
            
            while num < tag_length:
                tag[num] = tag[num].strip()
                sql = 'INSERT INTO gamedata_genre (TITLE, GENRE) VALUES (%s, %s)'
                cursor.execute(sql, (title, tag[num]))
                num += 1
        except:
            print(f"{title} DB 입력중 오류 발생(동일명 게임 존재)")

        beforetitle = title

        conn.commit()
        print(f'{next_page-1}p.{title} DB 입력 완료')
        driver.close()
        driver.switch_to.window(driver.window_handles[-1])

    #마지막페이지 크롤링 끝나면 break로 while문 빠져나옴
    if int(last_page)+1 == next_page:
        break
    
    sleep(1.5)

    #페이지 이동 실행하는 부분
    pagebar = driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
    pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')
    #화면 하단에 페이지 이동바의 숫자(2,3,4,...)와 next_page 변수값이 같다면 다음페이지가 존재하는것이니 다음페이지로 이동
    for page in pages:
        if page.text == str(next_page):
            page.click()
            next_page += 1
            restart += 1
            sleep(1.5)
            break

sleep(2)
print("크롤링 완료")
driver.quit()

"""
excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active
row_column = ["순위", "게임명", "정가", "할인가", "할인율", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\PlayStationSale\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

        data_column = [rank, title, price, saleprice, saleper, imgdata]
        excel_sheet.append(data_column)
        #dload.save(imgdata, f'{path}\IMG_{rank}.jpg')
        rank += 1

excel_File.save(f'{path}\PlayStationSale_{timestr}.xlsx')
excel_File.close()
"""