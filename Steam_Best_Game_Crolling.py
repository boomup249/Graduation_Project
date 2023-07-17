import Crolling_Parent_Class
from Crolling_Parent_Class import *

class Crolling_Steam_Best_Game(Crolling_Game_Info):
    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Crolling_Steam_Best_Game.xlsx')
        self.excel_File.close()
        
    #데이터 읽어와서 저장하는 함수
    def Data_Crolling(self):

        self.Open_Excel()

        html = self.driver.page_source
        soup = BeautifulSoup(html, "html.parser")

        panel = soup.find('div', attrs={'data-featuretarget': 'react-root'})
        gamelist = panel.select('tr.weeklytopsellers_TableRow_2-RN6')

        rank = 1
        for item in gamelist:
            title = item.select_one('div.weeklytopsellers_GameName_1n_4-')
            saleprice = item.select_one('div.salepreviewwidgets_StoreSalePriceBox_Wh0L8')
            price = item.select_one('div.salepreviewwidgets_StoreOriginalPrice_1EKGZ')
            saleper = item.select_one('div.salepreviewwidgets_StoreSaleDiscountBox_2fpFv')
            img = item.select_one('img.weeklytopsellers_CapsuleArt_2dODJ')

            if img != None:
                imgdata = img["src"]
            else:
                imgdata = "https://i.namu.wiki/i/j_EaOmOmU8QGpxXHpZGA75dSasZthOT5_X_nlFjUO3VwaxHuf0f5_0h0yKM8I65d9Jxwe74ynwTZRLfjSL8Yk0QCt871C6A-H-76SqzQ47QO2zGVf-6MJCITrIlds4vpPaG1fmZU3Ppyv12nf803tg.webp"

            if price == None:
                if saleprice != "무료 플레이":
                    price = saleprice.text
                    saleper = "X"
                else:
                    price = "무료"
                    saleper = "무료"
            else:
                price = price.text
                saleper = saleper.text

            data_column = [rank, title.text, price, saleprice.text, saleper, imgdata]
            self.excel_sheet.append(data_column)
            dload.save(imgdata, f'{self.path}\IMG_{rank}.jpg')
            rank += 1

        self.Close_Excel()