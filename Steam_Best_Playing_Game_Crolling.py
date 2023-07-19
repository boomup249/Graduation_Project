import Crolling_Parent_Class
from Crolling_Parent_Class import *

class Crolling_Steam_Best_Playing_Game(Crolling_Game_Info):
    #액셀 인스턴스 생성 함수
    def Open_Excel(self):
        self.excel_File = openpyxl.Workbook()
        self.excel_sheet = self.excel_File.active

        row_column = ["순위", "게임명", "정가", "할인가", "할인율", "현재 플레이어", "오늘 최다 동시접속자 수", "이미지 링크"]
        self.excel_sheet.append(row_column)

    #액셀 저장 후 인스턴스 제거 함수
    def Close_Excel(self):
        self.excel_File.save(f'{self.path}\Crolling_Steam_Best_Playing_Game.xlsx')
        self.excel_File.close()
        
    #데이터 읽어와서 저장하는 함수
    def Data_Crolling(self):
        self.Steam_Set_Language()
        sleep(5)
        self.Check_Mouse_Scroll()
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
            now_player = item.select_one('td.weeklytopsellers_ConcurrentCell_3L0CD')
            today_player = item.select_one('td.weeklytopsellers_PeakInGameCell_yJB7D')

            if img != None:
                imgdata = img["src"]
            else:
                imgdata = "https://i.namu.wiki/i/j_EaOmOmU8QGpxXHpZGA75dSasZthOT5_X_nlFjUO3VwaxHuf0f5_0h0yKM8I65d9Jxwe74ynwTZRLfjSL8Yk0QCt871C6A-H-76SqzQ47QO2zGVf-6MJCITrIlds4vpPaG1fmZU3Ppyv12nf803tg.webp"

            if saleprice != "무료 플레이":
                if saleprice == None:
                    price = "정보 없음"
                    saleprice = "정보 없음"
                    saleper = "정보 없음"
                elif price != None:
                    price = price.text
                    saleprice = saleprice.text
                    saleper = saleper.text
                else:
                    price = saleprice.text
                    saleprice = "할인 X"
                    saleper = "할인 X"
            else:
                price = "무료 플레이"
                saleprice = "무료"
                saleper = "무료"

            data_column = [rank, title.text, price, saleprice, saleper, now_player.text, today_player.text, imgdata]
            self.excel_sheet.append(data_column)
            dload.save(imgdata, f'{self.path}\IMG_{rank}.jpg')
            rank += 1

        self.Close_Excel()