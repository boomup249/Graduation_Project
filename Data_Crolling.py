import Steam_Best_Game_Crolling
from Steam_Best_Game_Crolling import *
import Steam_Sale_Game_Crolling
from Steam_Sale_Game_Crolling import *

#데이터 저장 경로에 현재시간 넣기위해 time, timestr 변수 선언
time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

#데이터 저장 경로 설정
Best_Game_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Steam_Best_Game'


#인기게임 크롤링 인스턴스 생성
Best_Game = Crolling_Steam_Best_Game(Best_Game_Path)
#객체에 URL 설정하고 실행
Best_Game.Set_URL("https://store.steampowered.com/charts/topselling")
Best_Game.Start_Crolling()
#인스턴스 해제
del Best_Game

#세일게임 크롤링 인스턴스 생성
Sale_Game_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Steam_Sale_Game'
#객체에 URL 설정하고 실행
Sale_Game = Crolling_Steam_Sale_Game(Sale_Game_Path)
Sale_Game.Set_URL("https://store.steampowered.com/specials/")
Sale_Game.Start_Crolling()
#인스턴스 해제
del Sale_Game