import Steam_Best_Popular_Game_Crawling
from Steam_Best_Popular_Game_Crawling import *
import Steam_Best_Playing_Game_Crawling
from Steam_Best_Playing_Game_Crawling import *
import Steam_Sale_Game_Crawling
from Steam_Sale_Game_Crawling import *
import EpicGames_Best_Playing_Crawling
from EpicGames_Best_Playing_Crawling import *
import EpicGames_Top_Seller_Crawling
from EpicGames_Top_Seller_Crawling import *
import Nintendo_Sale_Crawling
from Nintendo_Sale_Crawling import *
import Playstation_Sale_Crawling
from Playstation_Sale_Crawling import *

Steam_Best_Popular = Crawling_Steam_Best_Popular_Game()
Steam_Best_Playing = Crawling_Steam_Best_Playing_Game()
#Steam_Sale = Crawling_Steam_Sale_Game()
Epic_Best_Playing = Crawling_Epic_Best_Playing_Game()
Epic_Top_Seller = Crawling_Epic_Top_Seller_Game()
Nintendo_Sale = Crawling_Nintendo_Sale_Game()
Playstation_Sale = Crawling_Playstation_Sale_Game()

Steam_Best_Popular.Set_URL("https://store.steampowered.com/charts/topselling")
Steam_Best_Popular.Start_Crawling()
del Steam_Best_Popular

Steam_Best_Playing.Set_URL("https://store.steampowered.com/charts/mostplayed/")
Steam_Best_Playing.Start_Crawling()
del Steam_Best_Playing

"""
Steam_Sale.Set_URL("https://store.steampowered.com/specials/")
Steam_Sale.Start_Crawling()
del Steam_Sale
"""

Epic_Best_Playing.Set_URL('https://store.epicgames.com/ko/collection/most-played')
Epic_Best_Playing.Start_Crawling()
del Epic_Best_Playing

Epic_Top_Seller.Set_URL('https://store.epicgames.com/ko/collection/top-sellers')
Epic_Top_Seller.Start_Crawling()
del Epic_Top_Seller

Nintendo_Sale.Set_URL('https://store.nintendo.co.kr/games/sale')
Nintendo_Sale.Start_Crawling()
del Nintendo_Sale

Playstation_Sale.Set_URL('https://store.playstation.com/ko-kr/pages/deals')
Playstation_Sale.Start_Crawling()
del Playstation_Sale