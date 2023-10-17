import Base_Setting
from Base_Setting import *
import Steam_Sale_Game_Crawling
from Steam_Sale_Game_Crawling import *
import Nintendo_Sale_Crawling
from Nintendo_Sale_Crawling import *
import Playstation_Sale_Crawling
from Playstation_Sale_Crawling import *
import Epic_Sale_Game_Crawling
from Epic_Sale_Game_Crawling import *
import Danawa_Release_Crawling
from Danawa_Release_Crawling import *

base = Base_Setting()
del base

import schedule
import threading

mysql_passwd = "1937"

def run_daily_job():
    Steam_Sale = Crawling_Steam_Sale_Game()
    Epic_Sale = Crawling_Epic_Sale_Game()
    Nintendo_Sale = Crawling_Nintendo_Sale_Game()
    Playstation_Sale = Crawling_Playstation_Sale_Game()
    Danawa_Release = Crawling_Danawa_Release_Game()

    Steam_Sale.Set_mysql_connect(mysql_passwd)
    Steam_Sale.Set_URL_Platform('https://store.steampowered.com/specials/', 'steam')
    Epic_Sale.Set_mysql_connect(mysql_passwd)
    Epic_Sale.Set_URL_Platform('https://store.epicgames.com/ko/browse', 'epic')
    Nintendo_Sale.Set_mysql_connect(mysql_passwd)
    Nintendo_Sale.Set_URL_Platform('https://store.nintendo.co.kr/games/sale', 'switch')
    Playstation_Sale.Set_mysql_connect(mysql_passwd)
    Playstation_Sale.Set_URL_Platform('https://store.playstation.com/ko-kr/pages/deals', 'ps')
    Danawa_Release.Set_mysql_connect(mysql_passwd)
    Danawa_Release.Set_URL_Platform('https://prod.danawa.com/game/index.php', 'danawa')

    def start_crawling(instance):
        try:
            print(f"{instance.platform} 크롤링 시작")
            instance.Start_Crawling()
        except:
            pass

    threads = []
    instances = [Steam_Sale, Epic_Sale, Nintendo_Sale, Playstation_Sale, Danawa_Release]

    for instance in instances:
        thread = threading.Thread(target=start_crawling, args=(instance,))
        threads.append(thread)
        thread.start()

    for thread in threads:
        thread.join()

    del Steam_Sale
    del Epic_Sale
    del Nintendo_Sale
    del Playstation_Sale
    del Danawa_Release
    

# 스케줄러를 사용하여 1분마다 작업 예약
schedule.every().day.at("23:10:10").do(run_daily_job)

try:
    print("bot 실행 시작")
    while True:
        schedule.run_pending()
        pass
except KeyboardInterrupt:
    pass