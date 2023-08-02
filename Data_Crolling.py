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
import Nintendo_Sale_Crolling
from Nintendo_Sale_Crolling import *
import Playstation_Sale_Crolling
from Playstation_Sale_Crolling import *

Steam_Best_Popular = Crolling_Steam_Best_Popular_Game()
Steam_Best_Playing = Crolling_Steam_Best_Playing_Game()
Steam_Sale = Crolling_Steam_Sale_Game()
Epic_Best_Playing = Crolling_Epic_Best_Playing_Game()
Epic_Top_Seller = Crolling_Epic_Top_Seller_Game()
Nintendo_Sale = Crolling_Nintendo_Sale_Game()
Playstation_Sale = Crolling_Playstation_Sale_Game()

Steam_Best_Popular.Set_URL("https://store.steampowered.com/charts/topselling")
Steam_Best_Popular.Start_Crolling()
del Steam_Best_Popular

Steam_Best_Playing.Set_URL("https://store.steampowered.com/charts/mostplayed/")
Steam_Best_Playing.Start_Crolling()
del Steam_Best_Playing

"""
Steam_Sale.Set_URL("https://store.steampowered.com/specials/")
Steam_Sale.Start_Crolling()
del Steam_Sale
"""

Epic_Best_Playing.Set_URL('https://store.epicgames.com/ko/collection/most-played')
Epic_Best_Playing.Start_Crolling()
del Epic_Best_Playing

Epic_Top_Seller.Set_URL('https://store.epicgames.com/ko/collection/top-sellers')
Epic_Top_Seller.Start_Crolling()
del Epic_Top_Seller

Nintendo_Sale.Set_URL('https://store.nintendo.co.kr/games/sale')
Nintendo_Sale.Start_Crolling()
del Nintendo_Sale

Playstation_Sale.Set_URL('https://store.playstation.com/ko-kr/pages/deals')
Playstation_Sale.Start_Crolling()
del Playstation_Sale