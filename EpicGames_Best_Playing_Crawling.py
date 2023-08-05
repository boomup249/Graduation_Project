import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Epic_Best_Playing_Game(Crawling_Game_Info):
 #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Epic_Best_Playing_Game_Crolling.xlsx')
        self.excel_File.close()
        
    #데이터 읽어와서 저장하는 함수
    def Data_Crawling(self):
        sleep(5)
        self.Check_Mouse_Scroll_Send_Keys(20)
        self.Open_Excel()

        html = self.driver.page_source
        soup = BeautifulSoup(html, "html.parser")

        panel = soup.select_one("section.css-zjpm9r")
        gamelist = panel.select("div.css-lrwy1y")

        rank = 1

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
            if saleper == None:
                saleper = "X"
                saleprice = "X"
            else:
                price = item.find("div", class_="css-4jky3p").text
                saleprice = item.find("span", class_="css-119zqif").text
                saleper = saleper.text
            
            img = item.find("img", class_="css-174g26k")
            imgdata = img["src"]

            data_column = [rank, title, price, saleprice, saleper, imgdata]
            self.excel_sheet.append(data_column)
            rank += 1

        self.Close_Excel()