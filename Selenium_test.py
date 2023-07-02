import selenium
from time import sleep
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.action_chains import ActionChains
from bs4 import BeautifulSoup

#selenium 4.6 이상부턴 크롬드라이버의 경로를 지정해줄필요가 없음

#작업할 URL 설정
URL = 'https://www.naver.com'

#chromedriver 옵션 설정 // dd_experimental_option = 생성한 창이 꺼지지않게 만들어줌(ctrl + F5로 실행시 꺼지고, vscode 오른쪽 위 실행버튼으로 실행해야 안꺼짐)
options = Options()
options.add_experimental_option("detach", True)

#chromedriver 설정(순서대로 객체 생성, 윈도우 창 크기 조절, URL 지정)
driver = webdriver.Chrome(options=options)
driver.set_window_size(1400,1000)
driver.get(URL)
#암시적 대기 설정(페이지가 변경될시 10초동안 대기하는데 페이지가 로딩되면 바로 다음 명령어를 실행)
driver.implicitly_wait(10)

#네이버 검색창 아래 쇼핑 버튼 클릭
driver.find_element(By.XPATH, "/html/body/div[2]/div[1]/div/div[5]/ul/li[4]/a/span[2]").click()

#쇼핑 버튼을 누르면 새탭이 생기는데 해당 탭으로 이동하는 명령어
driver.switch_to.window(driver.window_handles[-1])

#오른쪽 상단 쇼핑 BEST 클릭
driver.find_element(By.XPATH, "/html/body/div[3]/div/div[1]/div/div/div[2]/div/div[2]/div/div[4]/a").click()

#현재 Best 판매상품들을 펼쳐보려면 '카테고리 베스트' 버튼을 눌러야해서 button 변수에 '카테고리 베스트' 버튼의 절대 경로를 설정
button = driver.find_element(By.XPATH, "/html/body/div/div/div[3]/div[2]/div[3]/a")
#이후 actions 변수에 '카테고리 베스트' 버튼의 위치가 보이게끔 페이지를 아래로 내리는 명령을 설정
acitons = ActionChains(driver).move_to_element(button)
#설정된 actions 명령을 실행하고 실행결과를 사용자가 확인할수있게 sleep으로 3초의 대기시간 지정
acitons.perform()
sleep(3)
#대기시간이 지나면 '카테고리 베스트' 버튼을 클릭하여 화면 이동
button.click()



#                       카테고리 베스트 페이지는 매번 정보가 바뀌는 동적 페이지기 때문에
#                       selenium으로 화면을 전부 불러온후 BeautifulSoup을 이용해 상품 정보를 크롤링해야한다



#pagedown 변수에 페이지 자체에 명령을 입력할수있게 body 태그를 설정
pagedown = driver.find_element(By.TAG_NAME, "body")
#이후 for문을 이용하여 화면을 최하단까지 드래그하여 모든 상품 정보를 읽어온다
#나중에 화면이 맨아래까지 내려온걸 체크하는 메서드를 찾으면 while문으로 바꾸면될듯
for i in range(15):
    pagedown.send_keys(Keys.PAGE_DOWN)
    sleep(0.5)

"""
    한 상품 전체 정보를 담고있는 태그 li class = "imageProduct_item__KZB_F"
    
        *모든 상품에 포함되는 정보*
    제품 이름 div class = "imageProduct_title__Wdeb1"
    제품 가격 = div class name : "imageProduct_price_W6pU1" 하단의 strong 태그
    제품 순위 = span class = "imageProduct_rank__lEppJ"
        *일부 상품에만 포함되는 정보*
    배달비 span class = "imageProduct_ico_delivery__OBgnG" // 배달비가 없을시 '판매처별 상이'로 출력
    판매자 div class = "imageProduct_mall__tJkQR" // 판매자가 없을시 '다양한 판매자'로 출력
"""

#BeautifulSoup을 사용하기위해 현재 페이지의 주소를 html 변수에 저장
html = driver.page_source
#soup 변수에 html을 넣고 BeautifulSoup 사용 선언
soup = BeautifulSoup(html, "html.parser")

#상품 전체 패널을 panel 변수에 저장
panel = soup.find("div", {"class": "category_panel"})
#product 변수에 상품 하나의 정보를 포함하고있는 li 태그를 전부 저장(배열)
product = panel.find_all("li", {"class", "imageProduct_item__KZB_F"})

for item in product:
    #제품명 div 태그를 name 변수에 저장
    name = item.find("div", {"class": "imageProduct_title__Wdeb1"})
    #제품 가격 strong 태그를 price 변수에 저장
    price = item.find("strong", {"class": None})
    #제품 순위 span 태그를 rank 변수에 저장
    rank = item.find("span", {"class": "imageProduct_rank__lEppJ"})
    
    #배달비 span 태그를 delivery_price 변수에 저장
    delivery_price = item.find("span", {"class": "imageProduct_ico_delivery__OBgnG"})
    #판매자 div 태그를 delivery_price 변수에 저장
    seller = item.find("div", {"class": "imageProduct_mall__tJkQR"})
    
    
    #이후 if문을 사용해서 배달비와 판매자가 적혀있는지 안적혀있는지 검사하고 존재하지않으면 62line 주석에서 정해둔 문자열값을 입력함
    """
    ★★★★★else문에서 읽어온 변수값을 str형식으로 형변환하는 이유★★★★★
    ★★★★★설명을 잘 못해서 개떡같이 써놨는데 어떻게든 이해하기바람★★★★★
    
    출력할때 형변환을 진행하면 if를 통과해 이미 문자열로 변환된 변수값을 다시 형변환하여 출력할것이다.
    
    문자열을 다시 문자열로 변환하려하면 오류가 생기기때문에
    if를 통과하든 else를 통과하든 변수는 문자열 형태로 변환시킨후
    출력할때 name, price와 다르게 .string이나 .get_text()를 붙이지않고 문자열 변수 자체를 출력하는게 맞다
    
    출력할때 name, price는 .string을 쓰고 delivery_price와 seller는 get_text()를 쓴 이유:
    여긴 공부가 필요하다 어려워서 설명을봐도 이해를 못했다(delivery_price, seller에 .string쓰면 오류나서 get_text()했는데 오류안남)
    """
    if delivery_price == None:
        delivery_price = "판매처별 상이"
    else:
        delivery_price = delivery_price.get_text()
    if seller == None:
        seller = "다양한 판매자"
    else:
        seller = seller.get_text()
    
    #랭킹, 제품명, 가격, 배달비, 판매자 순서로 출력
    print(rank.string, "- 제품명 :", name.string, ", 가격 :", price.string, ", 배달비 :", delivery_price, ", 판매자 :", seller, "\n")

#출력이 끝나면 10초 후 생성했던 driver 객체 제거하여 메모리 반환
sleep(10)
driver.quit()