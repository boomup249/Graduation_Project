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
import multiprocessing

def start_crawling(dict_list):
    mysql_passwd = "1234"
    URL = dict_list['URL']
    platform = dict_list['platform']

    if platform == 'steam':
        instance = Crawling_Steam_Sale_Game()
    elif platform == 'epic':
        instance = Crawling_Epic_Sale_Game()
    elif platform == 'switch':
        instance = Crawling_Nintendo_Sale_Game()
    elif platform == 'ps':
        instance = Crawling_Playstation_Sale_Game()
    elif platform == 'danawa':
        instance = Crawling_Danawa_Release_Game()

    instance.Set_mysql_connect(mysql_passwd)
    instance.Set_URL_Platform(URL, platform)

    try:
        print(f"{platform} 크롤링 시작")
        instance.Start_Crawling()
    except Exception as e:
        print(f'{platform} 크롤링 시작 오류 - ', e)

def run_daily_job():
    steam_dict = {'URL': 'https://store.steampowered.com/specials/', 'platform': 'steam'}
    steam_process = multiprocessing.Process(target=start_crawling, args=(steam_dict,))
    steam_process.start()
    sleep(10)
    
    processes = []
    instances = [
        {'URL': 'https://store.epicgames.com/ko/browse', 'platform': 'epic'},
        {'URL':'https://prod.danawa.com/game/index.php', 'platform': 'danawa'} 
        ]

    for instance in instances:
        process = multiprocessing.Process(target=start_crawling, args=(instance,))
        processes.append(process)
        process.start()
        sleep(3)

    for process in processes:
        process.join()
        del process
    
    
    processes = []
    instances = [
        {'URL': 'https://store.nintendo.co.kr/games/sale', 'platform': 'switch'},
        {'URL': 'https://store.playstation.com/ko-kr/pages/deals', 'platform': 'ps'}
        ]

    for instance in instances:
        process = multiprocessing.Process(target=start_crawling, args=(instance,))
        processes.append(process)
        process.start()
        sleep(3)

    for process in processes:
        process.join()
        del process
    
    steam_process.join()
    del steam_process

if __name__ == "__main__":
    multiprocessing.freeze_support()
    print("MYSQL root 비밀번호가 1234여야 작동됩니다")
    schedule.every().day.at("03:00").do(run_daily_job)
    base = Base_Setting()   
    del base

    try:
        print("bot 실행 시작")
        while True:
            schedule.run_pending()
            sleep(0.5)
    except KeyboardInterrupt:
        pass