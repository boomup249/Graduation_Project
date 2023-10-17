import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Epic_Sale_Game(Crawling_Game_Info):
    #데이터 읽어와서 저장하는 함수
    def Start_Crawling(self):
        epicgames = 'https://store.epicgames.com'

        driver = self.Driver_Start(self.platform, self.URL)
        soup = BeautifulSoup(driver.page_source, "html.parser")
        sleep(3)

        #할인 페이지 들어오자마자 페이지바 읽어와서 마지막 페이지 값 저장
        pages = driver.find_elements(By.CLASS_NAME, "css-12lid1g")
        #다음페이지 초기값(숫자)
        next_page = 2
        #마지막페이지 값(문자열)
        last_page = pages[6].text
        last_game_title = "title"

        for _ in range(10000000000):
            soup = BeautifulSoup(driver.page_source, "html.parser")
            panel = soup.select_one("section.css-zjpm9r")
            gamelist = panel.select("li.css-lrwy1y")
            
            #에픽은 하나의 driver 내에서 url을 변경하면 디도스로 인식해서 capcha가 불러와짐 이동하려면 quit()하고 이동해야함
            if driver:
                driver.quit()

            for item in gamelist:
                saleper = item.find("div", class_="css-b0xoos")
                if saleper == None:
                    continue
                else:
                    price = item.find("div", class_="css-4jky3p").text
                    saleprice = item.find("span", class_="css-119zqif").text
                    saleper = saleper.text

                title = item.find("div", class_="css-rgqwpc")
                if title == None:
                    title = item.find("div",class_="css-8uhtka").text
                else:
                    title = title.text

                #직전 크롤링한 게임의 타이틀과 현재 크롤링중인 게임의 타이틀이 동일하면 마지막페이지라서 break
                if last_game_title == title:
                    break

                game_link = item.select_one('a.css-g3jcms')["href"]
                move = epicgames+game_link
                print(f'{self.platform} - {title}로 이동')

                driver = self.Driver_Start(self.platform, move)
                sleep(3)
                new_soup = BeautifulSoup(driver.page_source, "html.parser")

                if new_soup.select_one('h1.css-1gty6cv') != None:
                    driver.quit()
                    continue

                descript = new_soup.select_one("div.css-1myreog")
                description = ""
                if descript != None:
                    descript = descript.text.strip()
                    for char in descript:
                        if char.isalnum() or char.isspace():
                            description += char

                img = new_soup.select_one('img.css-7i770w')
                if img != None:
                    imgdata = img["src"]

                gameimg_bar = new_soup.select_one('ul.css-elmzlf')
                if gameimg_bar != None:
                    gameimg = gameimg_bar.select_one('div.css-1q03292')
                    gameimg = gameimg.select_one('img')
                    if gameimg != None:
                        gameimg = gameimg["src"]
                else:
                    gameimg = new_soup.select_one('img.css-1bbjmcj')
                    if gameimg != None:
                        gameimg = gameimg["src"]

                data = {'TITLE' : title, 'PRICE' : price, 'SALEPRICE' : saleprice, 'SALEPER' : saleper, 'DESCRIPTION' : description, 'IMGDATA' : imgdata, 'GAMEIMG' : gameimg, 'URL' : move}
                self.dictionary_list.append(data)
                print(f'{self.platform} - {title}.append')

                tag = new_soup.select("li.css-t8k7")
                tag_length = len(tag)
                num = 0

                while num < tag_length:
                    tag[num] = tag[num].text.strip()
                    data_genre = {'TITLE' : title, 'GENRE' : tag[num]}
                    self.dictionary_list_genre.append(data_genre)
                    num += 1
                print(f'{self.platform} genre - {title}.append')

                driver.quit()

                #현재 페이지가 마지막 페이지면 last_game_title에 지금 크롤링중인 게임 타이틀 집어넣기
                if next_page-1 == int(last_page):
                    last_game_title = title
                sleep(1)
            
            if int(last_page)+1 == next_page:
                break

            #페이지 이동 실행하는 부분
            sleep(1)
            pagebar = soup.select_one('ul.css-zks4l')
            pages = pagebar.select('a.css-1ns6940')
            for page in pages:
                if page.text == str(next_page):
                    move_url = page["href"]
                    move = epicgames + move_url
                    next_page += 1
                    driver.quit()
                    driver = self.Driver_Start(self.platform, move)
                    sleep(5)
                    break

        driver.quit()
        self.Data_Crawling()

        if driver:
            driver.quit()