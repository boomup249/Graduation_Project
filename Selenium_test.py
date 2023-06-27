import selenium
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options

#작업할 URL 설정
URL = 'https://www.naver.com'

"""
chromedriver 옵션 설정
    add_experimental_option = 생성한 창이 꺼지지않게 만들어줌(ctrl + F5로 실행시 꺼지고, vscode 오른쪽 위 실행버튼으로 실행해야 안꺼짐)
"""
options = Options()
options.add_experimental_option("detach", True)

#chromedriver 객체 생성, URL 지정
driver = webdriver.Chrome('chromedriver.exe', options=options)
driver.get(URL)