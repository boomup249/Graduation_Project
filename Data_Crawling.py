import Steam_Sale_Game_Crawling
from Steam_Sale_Game_Crawling import *
import Nintendo_Sale_Crawling
from Nintendo_Sale_Crawling import *
import Playstation_Sale_Crawling
from Playstation_Sale_Crawling import *

Steam_Sale = Steam_Sale_Game_Crawling()
Nintendo_Sale = Crawling_Nintendo_Sale_Game()
Playstation_Sale = Crawling_Playstation_Sale_Game()

Steam_Sale.Set_URL("https://store.steampowered.com/specials/")
Steam_Sale.Start_Crawling()
del Steam_Sale

Nintendo_Sale.Set_URL('https://store.nintendo.co.kr/games/sale')
Nintendo_Sale.Start_Crawling()
del Nintendo_Sale

Playstation_Sale.Set_URL('https://store.playstation.com/ko-kr/pages/deals')
Playstation_Sale.Start_Crawling()
del Playstation_Sale