import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Epic_Top_Seller_Game(Crawling_Game_Info):
    #액셀 인스턴스 생성 함수
    def Open_Excel(self):
        self.excel_File = openpyxl.Workbook()
        self.excel_sheet = self.excel_File.active

        row_column = ["순위", "게임명", "정가", "파일 이미지"]
        self.excel_sheet.append(row_column)

    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Epic_Top_Seller_Game_Crolling.xlsx')
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
                title = item.find("div",class_="css-8uhtka")
            price = item.find("span", class_="css-119zqif")
            img = item.find("img", class_="css-174g26k")
            
            imgdata = img["src"]

            data_column = [rank, title.text, price.text, imgdata]
            self.excel_sheet.append(data_column)
            rank += 1

        self.Close_Excel()