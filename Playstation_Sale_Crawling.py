import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Playstation_Sale_Game(Crawling_Game_Info):
    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Playstation_Sale_Game_Crolling.xlsx')
        self.excel_File.close()

    #데이터 읽어와서 저장하는 함수
    def Data_Crawling(self):
        sleep(5)
        self.driver.find_element(By.XPATH, "//*[@id='main']/div/div[3]/section/div/ul/li[3]/a").click()
        sleep(2)
        html = self.driver.page_source
        soup = BeautifulSoup(html, "html.parser")

        pagebar = self.driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
        pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')
        rank = 1
        next_page = 2
        last_page = pages[4].text
        last_game_title = ""

        self.Open_Excel()

        while True:
            html = self.driver.page_source
            soup = BeautifulSoup(html, "html.parser")

            panel = soup.find("div", class_="psw-l-w-1/1")
            game_list = panel.find_all("li", class_="psw-l-w-1/2@mobile-s psw-l-w-1/2@mobile-l psw-l-w-1/6@tablet-l psw-l-w-1/4@tablet-s psw-l-w-1/6@laptop psw-l-w-1/8@desktop psw-l-w-1/8@max")

            for game in game_list:
                title = game.find("span", class_="psw-t-body psw-c-t-1 psw-t-truncate-2 psw-m-b-2").text
                if last_game_title == title:
                    break

                img = game.find("img", class_="psw-fade-in psw-top-left psw-l-fit-cover")
                imgdata = img["src"]

                saleper = game.find("span", class_="psw-body-2 psw-badge__text psw-badge--none psw-text-bold psw-p-y-0 psw-p-2 psw-r-1 psw-l-anchor")
                if saleper == None:
                    saleper = game.find("span", class_="psw-body-2 psw-badge__text psw-badge--ps-plus psw-c-bg-ps-plus psw-text-bold psw-p-y-0 psw-p-2 psw-r-1 psw-l-anchor")
                    price = "무료"
                    saleprice = "무료"
                    if saleper == None:
                        price = game.find("span", class_="psw-m-r-3").text
                        saleprice = "구매할 수 없음"
                        saleper = "구매할 수 없음"
                    else:
                        saleper = saleper.text
                else:
                    price = game.find("s").text
                    saleprice = game.find("span", class_="psw-m-r-3").text
                    saleper = saleper.text

                if int(last_page) == next_page-1 :
                    last_game_title = title

                data_column = [rank, title, price, saleprice, saleper, imgdata]
                self.excel_sheet.append(data_column)
                rank += 1

            if int(last_page)+1 == next_page:
                break

            pagebar = self.driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
            pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')
            for page in pages:
                if page.text == str(next_page):
                    page.click()
                    next_page += 1
                    sleep(3)
                    break

        self.Close_Excel()