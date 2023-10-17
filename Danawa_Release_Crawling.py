import Crawling_Parent_Class
from Crawling_Parent_Class import *

class Crawling_Danawa_Release_Game(Crawling_Game_Info):
    #데이터 읽어와서 저장하는 함수
    def Start_Crawling(self):
        driver = self.Driver_Start(self.platform, self.URL)
        action = ActionChains(driver)

        before_date = ""
        month = None

        for _ in range(100000000):
            soup = BeautifulSoup(driver.page_source, "html.parser")
            gamelist = soup.select_one('div.upc_tbl_wrap').select('tr')
            year = soup.select_one('em#nYear').text
            etc = None

            for list in gamelist:
                date = list.select_one('td.date')
                if date != None:
                    date = date.text.strip()
                    date = date.replace('.', '-')
                    date = re.sub(r'\(.\)', '', date)

                    if '년' in date:
                        month = None
                    elif '월' in date:
                        month = before_date[5:7]

                if date == "":
                    date = before_date

                platform = list.select_one('td.type')

                if platform != None:
                    platform = platform.text.strip()
                    if "XBOX" in platform:
                        continue

                title = list.select_one('a.tit_link')
                if title != None:
                    if date == None:
                        date = before_date
                    title = title.text.strip()

                price = list.select_one('em.num')

                if price == None:
                    price = list.select_one('span.p_txt')

                if price != None:
                    price = price.text.strip()
                
                before_date = date

                if date != None and not re.match(r'\d{4}-\d{2}-\d{2}', date):
                    date = None
                    if month != None:
                        etc = year + "-" + month + " 출시 예정"
                    else:
                        etc = year + " 출시 예정"

                if platform != None and title != None and price != None:
                    data = {'DATE' : date, 'TITLE' : title, 'PLATFORM' : platform, 'PRICE' : price, 'ETC' : etc}
                    self.dictionary_list.append(data)

            next_page_btn = driver.find_element(By.XPATH, '//*[@id="#nav_edge_next"]')
            action.click(next_page_btn).perform()

            sleep(3)

            new_soup = BeautifulSoup(driver.page_source, "html.parser")
            x = new_soup.select_one('p.n_txt')
            if x != None:
                break

        driver.quit()

        update_query = "UPDATE release_info SET VARIA = 0"
        self.cursor.execute(update_query)
        self.conn.commit()

        for data in self.dictionary_list:
            date = data['DATE']
            title = data['TITLE']
            platform = data['PLATFORM']
            price = data['PRICE']
            etc = data['ETC']
            # 데이터베이스에 해당 TITLE이 존재하는지 확인
            query = "SELECT * FROM release_info WHERE TITLE = %s AND platform = %s"
            self.cursor.execute(query, (title, platform))
            result = self.cursor.fetchone()

            if result is None:
                # 데이터베이스에 존재하지 않으면 INSERT
                insert_query = "INSERT INTO release_info (DATE, TITLE, PLATFORM, PRICE, ETC) VALUES (%s, %s, %s, %s, %s)"
                self.cursor.execute(insert_query, (date, title, platform, price, etc))
                self.conn.commit()
                print(f"INSERT: {title}")
            else:
                # 데이터베이스에 이미 존재하면 price를 비교하여 업데이트하고 varia 값을 1로 변경
                existing_price = result[3]  # 결과에서 현재 데이터베이스의 price 가져오기
                if existing_price != price:
                    update_query = "UPDATE release_info SET PRICE = %s, VARIA = 1 WHERE TITLE = %s AND PLATFORM = %s"
                    self.cursor.execute(update_query, (price, title, platform))
                    self.conn.commit()
                    print(f"UPDATE: {title} (Price || Varia Updated)")
                else:
                    update_query = "UPDATE release_info SET VARIA = 1 WHERE TITLE = %s AND PLATFORM = %s"
                    self.cursor.execute(update_query, (title, platform))
                    self.conn.commit()
                    print(f"UPDATE: {title} (Price Not Changed, VARIA UPDATE)")

        #varia값 변경이 전부끝났으면 0은 전부 삭제
        delete_query = "DELETE FROM release_info WHERE VARIA = 0"
        self.cursor.execute(delete_query)
        self.conn.commit()

        newtime = datetime.now()
        newtimestr = newtime.strftime("%Y%m%d_%H%M")

        sleep(2)
        print(self.platform, " 크롤링 및 DB 적용 완료 - 시작시간:", self.starttimestr, ", 완료시간:", newtimestr)

        if driver:
            driver.quit()