import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Nintendo_Sale_Game(Crawling_Game_Info):
    #saleper 계산 함수
    def saleper_calc(self, price, saleprice):
        price = int(price.strip('₩ ').replace(',', ''))
        saleprice = int(saleprice.strip('₩ ').replace(',', ''))
        _ = price - saleprice
        __ = int((_ / price) * 100)
        saleper = "-"+str(__)+"%"
        return saleper
    
    #데이터 읽어와서 저장하는 함수
    def Start_Crawling(self):
        driver = self.Driver_Start(self.platform, self.URL)

        soup = BeautifulSoup(driver.page_source, "html.parser")
        error = soup.select_one('h1')
        if error.text == '403 Forbidden':
            print("5분간 일시정지(403 forbidden 오류 회피를 위해)")
            driver.quit()
            sleep(300)
            print("재실행")
            driver = self.Driver_Start(self.platform, self.URL)

        sleep(5)
        driver.find_element(By.CLASS_NAME, 'popup-close').click()
        sleep(2)
        self.Check_Mouse_Scroll_Send_Keys(driver)
        soup = BeautifulSoup(driver.page_source, "html.parser")

        #게임 데이터 크롤링
        panel = soup.select_one("div.category-product-list")
        gamelist = panel.select("div.category-product-item")

        for item in gamelist:
            title = item.find("a", class_="category-product-item-title-link").text.strip()
            price = item.find("span", attrs={"data-price-type" : "oldPrice"})
            saleprice = item.find("span", attrs={"data-price-type" : "finalPrice"})
            img = item.find("img", class_="product-image-photo mplazyload mplazyload-transparent entered loaded")
            imgdata = img["src"]

            if price == None:
                continue
            else:
                price = price.find("span").text
                saleprice = saleprice.find("span").text
                saleper = self.saleper_calc(price, saleprice)

            game_link = item.select_one('a')
            if game_link != None:
                move = game_link["href"]

            driver.execute_script(f'window.open(\'{move}\');')
            driver.switch_to.window(driver.window_handles[-1])
            sleep(1.5)
            new_soup = BeautifulSoup(driver.page_source, "html.parser")

            error = new_soup.select_one('h1')
            if error.text == '403 Forbidden':
                print("5분간 일시정지(403 forbidden 오류 회피를 위해)")
                driver.close()
                driver.switch_to.window(driver.window_handles[-1])
                sleep(300)
                print("재실행")
                driver.execute_script(f'window.open(\'{move}\');')
                driver.switch_to.window(driver.window_handles[-1])
                sleep(5)
                new_soup = BeautifulSoup(driver.page_source, "html.parser")


            descript = new_soup.select_one("div.game_ex")
            description = ""
            if descript == None:
                descript = new_soup.select_one("div.value")
            else:
                descript = None
            
            if descript != None:
                descript = descript.text.strip()
                for char in descript:
                    if char.isalnum() or char.isspace():
                        description += char

            gameimg = new_soup.select_one('img.fotorama__img')  
            if gameimg != None:
                gameimg = gameimg["src"]

            data = {'TITLE' : title, 'PRICE' : price, 'SALEPRICE' : saleprice, 'SALEPER' : saleper, 'DESCRIPTION' : description, 'IMGDATA' : imgdata, 'GAMEIMG' : gameimg, 'URL' : move}
            self.dictionary_list.append(data)
            print(f'{self.platform} - {title}.append')

            tagparent = new_soup.find("div", class_="product-attribute game_category")
            if tagparent != None:
                tag = tagparent.find("div", class_="product-attribute-val").text.split(',')
                tag_length = len(tag)
                num = 0

                while num < tag_length:
                    tag[num] = tag[num].strip()
                    data_genre = {'TITLE' : title, 'GENRE' : tag[num]}
                    self.dictionary_list_genre.append(data_genre)
                    num += 1
            print(f'{self.platform} genre - {title}.append')

            driver.close()
            driver.switch_to.window(driver.window_handles[-1])

        driver.quit()
        self.Data_Crawling()

        if driver:
            driver.quit()