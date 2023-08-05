import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Nintendo_Sale_Game(Crawling_Game_Info):
    #액셀 인스턴스 생성 함수
    def Open_Excel(self):
        self.excel_File = openpyxl.Workbook()
        self.excel_sheet = self.excel_File.active

        row_column = ["게임명", "정가", "할인가", "이미지 링크"]
        self.excel_sheet.append(row_column)

    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\\Nintendo_Sale_Game_Crolling.xlsx')
        self.excel_File.close()

    #데이터 읽어와서 저장하는 함수
    def Data_Crawling(self):
        sleep(5)
        self.driver.find_element(By.CLASS_NAME, 'popup-close').click()
        sleep(2)
        self.Check_Mouse_Scroll_Send_Keys(100)
        self.Open_Excel()

        html = self.driver.page_source
        soup = BeautifulSoup(html, "html.parser")

        panel = soup.select_one("div.category-product-list")
        gamelist = panel.select("div.category-product-item")

        i = 1
        for item in gamelist:
            title = item.find("a", class_="category-product-item-title-link").text
            price = item.find("span", attrs={"data-price-type" : "finalPrice"})
            saleprice = item.find("span", attrs={"data-price-type" : "oldPrice"})
            img = item.find("img", class_="product-image-photo mplazyload mplazyload-transparent entered loaded")
            imgdata = img["src"]

            if price == None:
                price = item.find("span", class_="point-icon-wrapper")
                price = price.text + "포인트"
                saleprice = "포인트로 구매 가능"
            else:
                price = price.find("span").text
                saleprice = saleprice.find("span").text

            data_column = [title.strip(), price, saleprice, imgdata]
            self.excel_sheet.append(data_column)
            i += 1

        self.Close_Excel()