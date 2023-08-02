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
from time import sleep
from datetime import datetime

class Crolling_Game_Info:
    #생성자 __init__
    def __init__(self):
        #데이터 저장 경로에 현재시간 넣기위해 time, timestr 변수 선언
        time = datetime.now()
        timestr = time.strftime("%Y%m%d_%H%M")

        self.path = f'D:\Python\Study\Data_Crolling\{timestr}'
        Path(self.path).mkdir(parents=True, exist_ok=True)

        self.options = Options()
        self.options.add_experimental_option("detach", True)

        self.driver = webdriver

    #소멸자 __del__
    def __del__(self):
        print("인스턴스 해제 완료")

    #URL 설정 함수
    def Set_URL(self, url):
        self.URL = url

    #스팀 언어 설정 함수
    def Steam_Set_Language(self):
        html = self.driver.page_source
        soup = BeautifulSoup(html, "html.parser")

        steam_language = soup.find("span", {"class": "pulldown global_action_link"})

        if steam_language.get_text() != "언어":
            self.driver.find_element(By.XPATH, '//*[@id="language_pulldown"]').click()
            self.driver.find_element(By.XPATH, '//*[@id="language_dropdown"]/div/a[4]').click()

    #마우스 스크롤 내리는 함수
    def Check_Mouse_Scroll_Scroll_Down(self, number):
        for _ in range(0, number):
            self.driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')

    def Check_Mouse_Scroll_Send_Keys(self, number):
        page = self.driver.find_element(By.TAG_NAME, "body")
        for _ in range(0,number):
            page.send_keys(Keys.PAGE_DOWN)
            sleep(0.25)

    #액셀 인스턴스 생성 함수    
    def Open_Excel(self):
        self.excel_File = openpyxl.Workbook()
        self.excel_sheet = self.excel_File.active

        row_column = ["순위", "게임명", "정가", "할인가", "할인율", "파일 이미지"]
        self.excel_sheet.append(row_column)

    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        #오버라이딩으로 저장 파일명 수정해야함
        self.excel_File.save()
        self.excel_File.close()
        
    #데이터 읽어와서 저장하는 함수
    def Data_Crolling(self):

        self.Open_Excel()
        #오버라이딩으로 데이터 읽어와야함
        self.Close_Excel()

    #크롤링 시작 함수
    def Start_Crolling(self):
        self.driver = webdriver.Chrome(options=self.options)
        self.driver.set_window_size(1400,1000)
        self.driver.implicitly_wait(10)
        self.driver.get(self.URL)
        self.action = ActionChains(self.driver)

        self.Data_Crolling()
        self.driver.quit()
        print("크롤링 완료")