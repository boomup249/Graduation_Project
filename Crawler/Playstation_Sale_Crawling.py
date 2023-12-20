import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Playstation_Sale_Game(Crawling_Game_Info):

    #크롤링 시작 함수
    def Start_Crawling(self):
        gameURL = 'https://store.playstation.com/'
        driver = self.Driver_Start(self.platform, self.URL)
        sleep(5)
        #할인 페이지로 이동
        driver.find_element(By.XPATH, "//*[@id='main']/div/div[3]/section/div/ul/li[3]/a").click()
        sleep(5)
        print("ps 이동완료")
        soup = BeautifulSoup(driver.page_source, "html.parser")

        #할인 페이지 들어오자마자 페이지바 읽어와서 마지막 페이지 값 저장
        pagebar = driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
        pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')
        #다음페이지 초기값(숫자)
        next_page = 2
        #마지막페이지 값(문자열)
        last_page = pages[4].text
        #마지막 게임 이름 초기값(마지막페이지에 game_list가 한개 존재할경우 그 한개를 반복해서 저장하길래 그런상황에서 break를 걸기위해 변수 하나 생성)
        last_game_title = ""
        #db에 넣을때 title이 중복되면 오류나서 중복되는 게임은 스킵하기위해 beforetitle 선언
        beforetitle = ""
        restart = 1

        for _ in range(100000000):
            #driver 하나에서 크롤링을 지속하면 점점 렉걸려서 조건 걸어서 driver 재시작하는부분
            if(restart > 4):
                new_url = driver.current_url
                driver.quit()
                driver = self.Driver_Start(self.platform, new_url)
                print("셀레니움 재실행(렉방지)")
                sleep(3)
                restart = 1

            #페이지가 변경됐을때 변경된 페이지를 파싱
            soup = BeautifulSoup(driver.page_source, "html.parser")
            sleep(1.5)

            panel = soup.find("div", class_="psw-l-w-1/1")
            game_list = panel.find_all("li", class_="psw-l-w-1/2@mobile-s psw-l-w-1/2@mobile-l psw-l-w-1/6@tablet-l psw-l-w-1/4@tablet-s psw-l-w-1/6@laptop psw-l-w-1/8@desktop psw-l-w-1/8@max")
            for game in game_list:
                title = game.find("span", class_="psw-t-body psw-c-t-1 psw-t-truncate-2 psw-m-b-2")
                if title != None:
                    title = title.text

                if title == beforetitle:
                    continue
                #last_game_title의 값과 현재 크롤링하는 게임의 title의 값이 같으면 크롤링 종료
                if last_game_title == title:
                    break

                img = game.find("img", class_="psw-fade-in psw-top-left psw-l-fit-cover")

                if img != None:
                    imgdata = img["src"]
                
                saleper = game.find("span", class_="psw-body-2 psw-badge__text psw-badge--none psw-text-bold psw-p-y-0 psw-p-2 psw-r-1 psw-l-anchor")

                #saleper가 None이면 할인율은 무조건 -100%기때문에 price, saleprice = 무료
                if saleper == None:
                    saleper = game.find("span", class_="psw-body-2 psw-badge__text psw-badge--ps-plus psw-c-bg-ps-plus psw-text-bold psw-p-y-0 psw-p-2 psw-r-1 psw-l-anchor")
                    price = "무료"
                    saleprice = "무료"
                    #-100% 할인도 적혀있지않다면 구매할수없는 게임이기에 정가, 할인가, 할인율의 값을 "구매할 수 없음"으로 설정
                    if saleper == None:
                        price = None
                        saleprice = game.find("span", class_="psw-m-r-3")
                        saleper = None
                        if saleprice != None:
                            saleprice = saleprice.text
                        else:
                            price = None
                            saleprice = "구매할 수 없음"
                            saleper = None
                    else:
                        saleper = saleper.text
                #saleper가 None이 아니였기때문에 price와 saleprice를 크롤링
                else:
                    price = game.find("s").text
                    saleprice = game.find("span", class_="psw-m-r-3").text
                    saleper = saleper.text

                #현재 페이지와 마지막 페이지의 숫자가 똑같다면 last_game_title에 방금 크롤링한 게임 이름을 저장함
                if next_page-1 == int(last_page):
                    last_game_title = title

                game_link = game.select_one('a')
                if game_link != None:
                    game_link = game_link['href']
                    move = gameURL + game_link
                else:
                    move = None

                driver.execute_script(f'window.open(\'{move}\');')
                driver.switch_to.window(driver.window_handles[-1])
                sleep(1.5)
                new_soup = BeautifulSoup(driver.page_source, "html.parser")
                sleep(1.5)

                descript = new_soup.find('p', attrs={'data-qa': 'mfe-game-overview#description'})

                if descript != None:
                    descript = descript.text.strip()
                    description = ""
                    for char in descript:
                        if char.isalnum() or char.isspace():
                            description += char

                gameimg = new_soup.find('img', attrs={'data-qa': 'gameBackgroundImage#heroImage#image'})
                if gameimg == None:
                    gameimg = new_soup.find('img', attrs={'data-qa': 'gameBackgroundImage#tileImage#image'})

                if gameimg != None:
                    gameimg = gameimg["src"]
                
                sleep(0.5)

                data = {'TITLE' : title, 'PRICE' : price, 'SALEPRICE' : saleprice, 'SALEPER' : saleper, 'DESCRIPTION' : description, 'IMGDATA' : imgdata, 'GAMEIMG' : gameimg, 'URL' : move}
                self.dictionary_list.append(data)
                print(f'{self.platform} - {title}.append')
                
                tagparent = new_soup.find("dd", {'data-qa':'gameInfo#releaseInformation#genre-value'})
                if tagparent != None:
                    tag = tagparent.select_one("span").text.split(',')
                    tag_length = len(tag)
                    num = 0
                
                while num < tag_length:
                    tag[num] = tag[num].strip()
                    data_genre = {'TITLE' : title, 'GENRE' : tag[num]}
                    self.dictionary_list_genre.append(data_genre)
                    num += 1
                print(f'{self.platform} genre - {title}.append')

                beforetitle = title

                driver.close()
                driver.switch_to.window(driver.window_handles[-1])

            #마지막페이지 크롤링 끝나면 break로 while문 빠져나옴
            if int(last_page)+1 == next_page:
                break
            
            sleep(1.5)
            
            #페이지 이동 실행하는 부분
            pagebar = driver.find_element(By.CLASS_NAME, "psw-l-stack-center")
            pages = pagebar.find_elements(By.CSS_SELECTOR, 'li')
            #화면 하단에 페이지 이동바의 숫자(2,3,4,...)와 next_page 변수값이 같다면 다음페이지가 존재하는것이니 다음페이지로 이동
            for page in pages:
                if page.text == str(next_page):
                    page.click()
                    next_page += 1
                    restart += 1
                    sleep(1.5)
                    break

        driver.quit()

        self.Data_Crawling()

        if driver:
            driver.quit()