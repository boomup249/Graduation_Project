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

time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

URL = 'https://store.playstation.com/ko-kr/pages/deals'

#크롬드라이버 옵션 설정
options = Options()
options.add_experimental_option("detach", True)

#크롬드라이버 인스턴스 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,1000)
driver.get(URL)
driver.implicitly_wait(10)

excel_File = openpyxl.Workbook()
excel_sheet = excel_File.active
row_column = ["순위", "게임명", "정가", "할인가", "할인율", "파일 이미지"]
excel_sheet.append(row_column)

path = f'D:\Python\Study\PlayStationSale\{timestr}'
Path(path).mkdir(parents=True, exist_ok=True)

#할인 페이지로 접속(할인페이지 url을보니 변경될수도 있을거같아서 이렇게 접속)
driver.find_element(By.XPATH, "//*[@id='main']/div/div[3]/section/div/ul/li[3]/a").click()
sleep(2)

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#할인 페이지 들어오자마자 페이지바 읽어와서 마지막 페이지 값 저장
pagebar = driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')

#게임순위 초기값
rank = 1
#다음페이지 초기값(숫자)
next_page = 2
#마지막페이지 값(문자열)
last_page = pages[4].text
#마지막 게임 이름 초기값(마지막페이지에 game_list가 한개 존재할경우 그 한개를 반복해서 저장하길래 그런상황에서 break를 걸기위해 변수 하나 생성)
last_game_title = ""

while True:
    #페이지가 변경됐을때 변경된 페이지를 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, "html.parser")

    panel = soup.find("div", class_="psw-l-w-1/1")
    game_list = panel.find_all("li", class_="psw-l-w-1/2@mobile-s psw-l-w-1/2@mobile-l psw-l-w-1/6@tablet-l psw-l-w-1/4@tablet-s psw-l-w-1/6@laptop psw-l-w-1/8@desktop psw-l-w-1/8@max")

    for game in game_list:
        title = game.find("span", class_="psw-t-body psw-c-t-1 psw-t-truncate-2 psw-m-b-2").text
        #last_game_title의 값과 현재 크롤링하는 게임의 title의 값이 같으면 크롤링 종료
        if last_game_title == title:
            break
        img = game.find("img", class_="psw-fade-in psw-top-left psw-l-fit-cover")
        imgdata = img["src"]
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

        data_column = [rank, title, price, saleprice, saleper, imgdata]
        excel_sheet.append(data_column)
        #dload.save(imgdata, f'{path}\IMG_{rank}.jpg')
        rank += 1

    #마지막페이지 크롤링 끝나면 break로 while문 빠져나옴
    if int(last_page)+1 == next_page:
        break

    #페이지 이동 실행하는 부분
    pagebar = driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
    pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')
    #화면 하단에 페이지 이동바의 숫자(2,3,4,...)와 next_page 변수값이 같다면 다음페이지가 존재하는것이니 다음페이지로 이동
    for page in pages:
        if page.text == str(next_page):
            page.click()
            next_page += 1
            sleep(3)
            break

excel_File.save(f'{path}\PlayStationSale_{timestr}.xlsx')
excel_File.close()
driver.quit()