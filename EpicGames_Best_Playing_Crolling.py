import Crolling_Parent_Class
from Crolling_Parent_Class import *

class Crolling_Epic_Best_Playing_Game(Crolling_Game_Info):
    #마우스 스크롤 내리는 함수
    def Check_Mouse_Scroll(self):
        for _ in range(0,20):
            page = self.driver.find_element(By.TAG_NAME, "body")
            page.send_keys(Keys.PAGE_DOWN)

    #액셀 인스턴스 생성 함수
    def Open_Excel(self):
        self.excel_File = openpyxl.Workbook()
        self.excel_sheet = self.excel_File.active

        row_column = ["순위", "게임명", "정가", "할인가", "할인율", "파일 이미지"]
        self.excel_sheet.append(row_column)

    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Crolling_Epic_Best_Playing_Game.xlsx')
        self.excel_File.close()
        
    #데이터 읽어와서 저장하는 함수
    def Data_Crolling(self):
        sleep(5)
        self.Check_Mouse_Scroll()
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
            if price == None:
                price = "정보 없음"
            else:
                price = price.text
            
            saleper = item.find("div", class_="css-b0xoos")
            if saleper != None:
                price = item.find("div", class_="css-4jky3p").text
                saleprice = item.find("span", class_="css-119zqif").text
                saleper = saleper.text
            else:
                saleper = "X"
                saleprice = "X"
            
            img = item.find("img", class_="css-174g26k")
            imgdata = img["src"]

            data_column = [rank, title.text, price, saleprice, saleper, imgdata]
            self.excel_sheet.append(data_column)
            dload.save(imgdata, f'{self.path}\IMG_{rank}.jpg')
            rank += 1

        self.Close_Excel()