import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Steam_Sale_Game(Crawling_Game_Info):
    #더 보기 버튼 찾아서 리턴하는 함수
    def find_element_by_css(self, driver, css):
        try:
            return driver.find_element(By.CSS_SELECTOR, css)
        except NoSuchElementException as _:
            return None
    
    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Steam_Sale_Game_Crolling.xlsx')
        self.excel_File.close()
        
    #데이터 읽어와서 저장하는 함수
    def Data_Crawling(self):
        self.Steam_Set_Language()
        sleep(5)
        self.Check_Mouse_Scroll_Scroll_Down(1)
        self.Open_Excel()

        while True:
            button = self.find_element_by_css(self.driver, '#SaleSection_13268 > div.partnersaledisplay_SaleSection_2NfLq.eventbbcodeparser_SaleSectionCtn_2Xrw_.SaleSectionForCustomCSS > div.saleitembrowser_SaleItemBrowserContainer_2wLns > div:nth-child(2) > div.facetedbrowse_FacetedBrowseInnerCtn_hWbTI > div > div.saleitembrowser_ShowContentsContainer_3IRkb > button')
            if button != None:
                self.action.move_to_element(button).click(button).perform()
            else:
                break

        sleep(1)
        self.driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
        html = self.driver.page_source
        soup = BeautifulSoup(html, "html.parser")

        panel = soup.select_one("div.saleitembrowser_SaleItemBrowserContainer_2wLns")
        gamelist = panel.select("div.salepreviewwidgets_SaleItemBrowserRow_y9MSd")

        rank = 1
        for item in gamelist:
            title = item.find("div", class_="salepreviewwidgets_StoreSaleWidgetTitle_3jI46 StoreSaleWidgetTitle").text
            price = item.find("div", class_="salepreviewwidgets_StoreOriginalPrice_1EKGZ")
            saleprice = item.find("div", class_="salepreviewwidgets_StoreSalePriceBox_Wh0L8").text
            saleper = item.find("div", class_="salepreviewwidgets_StoreSaleDiscountBox_2fpFv")
            img = item.find("img", class_="salepreviewwidgets_CapsuleImage_cODQh")
            
            imgdata = img["src"]
            if price == None:
                price = "할인 X"
                saleper = "할인 X"
            else:
                price = price.text
                saleper = saleper.text

            data_column = [rank, title, price, saleprice, saleper, imgdata]
            self.excel_sheet.append(data_column)
            rank += 1

        self.Close_Excel()