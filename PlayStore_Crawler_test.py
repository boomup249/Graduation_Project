import requests
from bs4 import BeautifulSoup

"""                                             
게임 이미지 : img src="~" class="T75of etjhNc Q8CSx "
게임 이름 : div class="Epkrse "                     
게임 별점 : div class="LrNMN"                       
게임 가격                                           
	원래 가격 : span class="SUZt4c P8AFK" 아래 span   
	할인 가격 : span class="VfPpfd VixbEe" 아래 span  
"""

url = "https://play.google.com/store/apps/collection/promotion_3002c9b_gamesDynamic_cyberweek2017?clp=CjQKMgoscHJvbW90aW9uXzMwMDJjOWJfZ2FtZXNEeW5hbWljX2N5YmVyd2VlazIwMTcQShgD:S:ANO1ljI2ADA&gsr=CjYKNAoyCixwcm9tb3Rpb25fMzAwMmM5Yl9nYW1lc0R5bmFtaWNfY3liZXJ3ZWVrMjAxNxBKGAM%3D:S:ANO1ljK6Aoo&hl=ko&gl=US"
result = requests.get(url)

bs_obj = BeautifulSoup(result.content, "html.parser")
game_list = bs_obj.find("div", {"class": "ftgkle"})
game = game_list.find_all("div", {"class", "ULeU3b"})

for item in game:
    name = item.find("div", {"class": None})
    rank = item.find("div", {"class": "LrNMN"})
    price = item.find("span", {"class": "SUZt4c P8AFK"})
    price_span = price.find("span")
    sale = item.find("span", {"class": "VfPpfd VixbEe"})
    sale_span = sale.find("span")

    img = item.find("div", {"class": "TjRVLb"})
    img1 = img.find("img")['src']

    print("게임 이름 : ", name.text, ", 별점 : ", rank.text, ", 정가 : ", price_span.text, ", 할인가 : ", sale_span.text, ", 이미지 링크 : ", img1)