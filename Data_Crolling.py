import Steam_Best_Popular_Game_Crolling
from Steam_Best_Popular_Game_Crolling import *
import Steam_Best_Playing_Game_Crolling
from Steam_Best_Playing_Game_Crolling import *
import Steam_Sale_Game_Crolling
from Steam_Sale_Game_Crolling import *
import EpicGames_Best_Playing_Crolling
from EpicGames_Best_Playing_Crolling import *
import EpicGames_Top_Seller_Crolling
from EpicGames_Top_Seller_Crolling import *

#데이터 저장 경로에 현재시간 넣기위해 time, timestr 변수 선언
time = datetime.now()
timestr = time.strftime("%Y%m%d_%H%M")

Best_Popular_Game_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Steam_Best_Popular_Game'
Best_Popular_Game = Crolling_Steam_Best_Popular_Game(Best_Popular_Game_Path)
Best_Popular_Game.Set_URL("https://store.steampowered.com/charts/topselling")
Best_Popular_Game.Start_Crolling()
del Best_Popular_Game

Best_Playing_Game_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Steam_Best_Playing_Game'
Best_Playing_Game = Crolling_Steam_Best_Playing_Game(Best_Playing_Game_Path)
Best_Playing_Game.Set_URL("https://store.steampowered.com/charts/mostplayed/")
Best_Playing_Game.Start_Crolling()
del Best_Playing_Game

Epic_Best_Playing_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Epic_Best_Playing_Game'
Epic_Best_Playing = Crolling_Epic_Best_Playing_Game(Epic_Best_Playing_Path)
Epic_Best_Playing.Set_URL('https://store.epicgames.com/ko/collection/most-played')
Epic_Best_Playing.Start_Crolling()
del Epic_Best_Playing

Epic_Top_Seller_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Epic_Top_Seller_Game'
Epic_Top_Seller = Crolling_Epic_Top_Seller_Game(Epic_Top_Seller_Path)
Epic_Top_Seller.Set_URL('https://store.epicgames.com/ko/collection/top-sellers')
Epic_Top_Seller.Start_Crolling()
del Epic_Top_Seller

"""
#세일게임 데이터 저장 경로 설정
Sale_Game_Path = f'D:\Python\Study\Data_Crolling\{timestr}\Steam_Sale_Game'
#세일게임 크롤링 인스턴스 생성
Sale_Game = Crolling_Steam_Sale_Game(Sale_Game_Path)
#객체에 URL 설정하고 실행
Sale_Game.Set_URL("https://store.steampowered.com/specials/")
Sale_Game.Start_Crolling()
#인스턴스 해제
del Sale_Game
"""