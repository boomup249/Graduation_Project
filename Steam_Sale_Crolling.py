import selenium
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from bs4 import BeautifulSoup

from time import sleep

#스팀 상점 URL 설정
URL = 'https://store.steampowered.com/specials/'

#크롬드라이버 옵션 설정
options = Options()
options.add_experimental_option("detach", True)

#크롬드라이버 객체 생성 및 옵션, 크기, URL, 암시적 대기 설정
driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,1000)
driver.get(URL)
driver.implicitly_wait(10)

action = ActionChains(driver)

#현재 페이지 주소 html에 설정, BeautilfulSoup으로 페이지 parsing
html = driver.page_source
soup = BeautifulSoup(html, "html.parser")

#정보를 한글로 크롤링하기위해 페이지 언어 변경
steam_language = soup.find("span", {"class": "pulldown global_action_link"})

if steam_language.string != "언어":
    driver.find_element(By.XPATH, "/html/body/div[1]/div[7]/div[3]/div/div[3]/div/span").click()
    driver.find_element(By.XPATH, "/html/body/div[1]/div[7]/div[3]/div/div[3]/div/div/div/a[4]").click()
sleep(5)



"""
# 현재 페이지 스크롤 전체 길이 추출
driver.execute_script('return document.body.scrollHeight')
'
# 현재 스크롤 좌표 추출
driver.execute_script('return window.pageYOffset')

# 지정 좌표로 스크롤 이동
driver.execute_script('window.scrollTo(x, y)')

#스크롤을 제일 아래까지 내리는 제어 반복문 // steam에서 스크롤이 반환받은 scrollHeigth까지 안내려가서 폐기
scroll_location = driver.execute_script('return document.body.scrollHeight')
while True:
    driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
    sleep(1.5)
    scroll_height = driver.execute_script('return window.pageYOffset')

    if scroll_location == scroll_height:
        break
    else:
        scroll_location = driver.execute_script('return document.body.scrollHeight')
"""

#스크롤을 최하단까지 중간에 1.5초의 텀을주고 두번 내려서 동적으로 생성되는 객체들을 불러온후 스크롤을 최하단으로 내림
#스크롤을 내리는 이유 : 할인 상품들은 동적으로 생성되서 스크롤을 내려 생성시킨후 '더 보기' 버튼 객체를 찾기위함
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(1.5)
driver.execute_script('window.scrollTo(0,document.body.scrollHeight)')
sleep(1.5)

#할인 상품전체 패널
panel = soup.find("div", {"class": "partnersaledisplay_SaleSection_2NfLq eventbbcodeparser_SaleSectionCtn_2Xrw_ SaleSectionForCustomCSS"})

#현재 할인중인 상품들을 펼쳐보려면 '더 보기' 버튼을 눌러야해서 button 변수에 '더 보기' 버튼의 경로를 설정
while True:
    button = driver.find_element(By.CSS_SELECTOR, "#SaleSection_13268 > div.partnersaledisplay_SaleSection_2NfLq.eventbbcodeparser_SaleSectionCtn_2Xrw_.SaleSectionForCustomCSS > div.saleitembrowser_SaleItemBrowserContainer_2wLns > div:nth-child(2) > div.facetedbrowse_FacetedBrowseInnerCtn_hWbTI > div > div.saleitembrowser_ShowContentsContainer_3IRkb > button")
    if button != None:
        action.move_to_element(button).perform()
        button.click()
        sleep(2)
    else:
        break

sleep(5)
driver.quit()