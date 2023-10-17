import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Steam_Sale_Game(Crawling_Game_Info):
    #크롤링 시작 함수
    def Start_Crawling(self):
        btn_num = 1
        btn_check = 1
        mode = 0
        for _ in range(10000000000):
            driver = self.Driver_Start(self.platform, self.URL)
            action = ActionChains(driver)
            soup = BeautifulSoup(driver.page_source, 'html.parser')
            sleep(5)
            steam_language = soup.find("span", {"class": "pulldown global_action_link"})
            if steam_language.get_text() != "언어":
                driver.find_element(By.XPATH, '//*[@id="language_pulldown"]').click()
                driver.find_element(By.XPATH, '//*[@id="language_dropdown"]/div/a[4]').click()
                print("언어설정 완료")
            sleep(5)
            self.Check_Mouse_Scroll_Down(driver)

            if mode == 0:
                #반복문으로 할인중인 게임 목록에 '더 보기' 버튼이 존재하면 해당 버튼을 누르고 없어지면 반복문을 빠져나오게 작성
                for __ in range(10000000000):
                    button = self.find_element_by_css(driver, '#SaleSection_13268 > div.partnersaledisplay_SaleSection_2NfLq.eventbbcodeparser_SaleSectionCtn_2Xrw_.SaleSectionForCustomCSS > div.saleitembrowser_SaleItemBrowserContainer_2wLns > div:nth-child(2) > div.facetedbrowse_FacetedBrowseInnerCtn_hWbTI > div > div.saleitembrowser_ShowContentsContainer_3IRkb > button')
                    if btn_num < 11:
                        #if else문 : '더 보기' 버튼이 존재하면(None 타입이 아니라 WebElement 타입이 반환되면)
                        if button != None:
                            #화면을 '더 보기' 버튼이 있는곳으로 이동시킨 후 버튼을 클릭한다
                            try:
                                action.move_to_element(button).click().perform()
                            except StaleElementReferenceException as e:
                                print(f"버튼 없음 - {e}")
                                mode = 1
                                break
                            print("steam 버튼 클릭 - ", btn_num)
                            btn_num += 1
                            btn_check += 1
                        else: #'더 보기' 버튼이 존재하지않으면 break로 반복문 빠져나옴
                            print("버튼 전부 클릭 완료 - ", btn_check, "번 실행")
                            self.Data_Crawling(driver)
                            mode = 1
                            break
                    else:
                        self.Data_Crawling(driver)
                        try:
                            action.move_to_element(button).click().perform()
                        except StaleElementReferenceException as e:
                            print(f"버튼 없음 - {e}")
                            mode = 1
                            break
                        sleep(1.5)
                        self.URL = driver.current_url
                        btn_num = 1
                        break

                driver.quit()

            elif mode == 1:
                self.Insert_GameData()
                self.Insert_GenreData()
                newtime = datetime.now()
                newtimestr = newtime.strftime("%Y%m%d_%H%M")
                sleep(2)
                print(self.platform, " 크롤링 및 DB 적용 완료 - 시작시간:", self.starttimestr, ", 완료시간:", newtimestr)
                break

        if driver:
            driver.quit()

    #크롤링하고 데이터 저장 함수
    def Data_Crawling(self, driver):
        driver.execute_script("window.scrollTo(0,document.body.scrollHeight)")
        soup = BeautifulSoup(driver.page_source, "html.parser")

        panel = soup.select_one("div.saleitembrowser_SaleItemBrowserContainer_2wLns")
        gamelist = panel.select("div.salepreviewwidgets_SaleItemBrowserRow_y9MSd")

        for item in gamelist:
            title = item.find("div", class_="salepreviewwidgets_StoreSaleWidgetTitle_3jI46 StoreSaleWidgetTitle").text
            price = item.find("div", class_="salepreviewwidgets_StoreOriginalPrice_1EKGZ")
            saleprice = item.find("div", class_="salepreviewwidgets_StoreSalePriceBox_Wh0L8")
            saleper = item.find("div", class_="salepreviewwidgets_StoreSaleDiscountBox_2fpFv")
            
            game_link = item.select_one('div.salepreviewwidgets_StoreSaleWidgetHalfLeft_2Va3O > a')
            move = game_link["href"]
            
            #게임은 할인하는데 확장팩이 할인안하는 경우 price와 saleper가 안적혀있어서 if문 작성
            if price == None:
                price = None
                saleprice = price.text
                saleper = None
            else:
                price = price.text
                saleprice = saleprice.text
                saleper = saleper.text

            driver.execute_script(f'window.open(\'{move}\');')
            driver.switch_to.window(driver.window_handles[-1])
            sleep(3)
            new_soup = BeautifulSoup(driver.page_source, "html.parser")

            check = new_soup.select_one("div.agegate_btn_ctn")
            if check != None:
                driver.find_element(By.XPATH, '//*[@id="ageYear"]').click()
                driver.find_element(By.XPATH, '//*[@id="ageYear"]/option[88]').click()
                driver.find_element(By.XPATH, '//*[@id="view_product_page_btn"]/span').click()
                sleep(1.5)
                new_soup = BeautifulSoup(driver.page_source, "html.parser")
                sleep(1.5)
            imgdata = new_soup.select_one('img.game_header_image_full')

            if imgdata == None:
                driver.execute_script(f'window.open(\'{move}\');')
                driver.switch_to.window(driver.window_handles[-1])
                if self.find_element_by_css(driver, '#ageYear') != None:
                    driver.find_element(By.XPATH, '//*[@id="ageYear"]').click()
                    driver.find_element(By.XPATH, '//*[@id="ageYear"]/option[88]').click()
                    driver.find_element(By.XPATH, '//*[@id="view_product_page_btn"]/span').click()
                    sleep(1.5)
                new_soup = BeautifulSoup(driver.page_source, "html.parser")
                sleep(1.5)
                imgdata = new_soup.select_one('img.game_header_image_full') 
                if imgdata == None:
                    imgdata = new_soup.select_one('img.package_header')

            if imgdata != None:
                imgdata = imgdata["src"]

            gameimg = new_soup.select_one('a.highlight_screenshot_link')

            if gameimg != None:
                gameimg = gameimg['href']

            descript = new_soup.select_one("div.game_description_snippet")
            description = ""
            if descript != None:
                descript = descript.text.strip()
                for char in descript:
                    if char.isalnum() or char.isspace():
                        description += char

            tag = new_soup.select("a.app_tag")
            
            tag_length = len(tag)
            num = 0

            data = {'TITLE' : title, 'PRICE' : price, 'SALEPRICE' : saleprice, 'SALEPER' : saleper, 'DESCRIPTION' : description, 'IMGDATA' : imgdata, 'GAMEIMG' : gameimg, 'URL' : move}
            self.dictionary_list.append(data)
            print(f'{self.platform} - {title}.append')

            while num < tag_length:
                tag[num] = tag[num].text.strip()
                data_genre = {'TITLE' : title, 'GENRE' : tag[num]}
                self.dictionary_list_genre.append(data_genre)
                num += 1
            print(f'{self.platform} genre - {title}.append')

            #탭 종료후 원래 탭(베스트게임 페이지)으로 이동
            sleep(0.5)
            driver.close()
            driver.switch_to.window(driver.window_handles[-1])