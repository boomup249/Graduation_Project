import MySQLdb

conn = MySQLdb.connect(
    user="root",
    passwd="1937",
    host="localhost",
    db="member"
)
cursor = conn.cursor()

# 모든 레코드의 varia 값을 0으로 업데이트
update_query = "UPDATE release_info SET VARIA = 0"
cursor.execute(update_query)
conn.commit()

dictionary_list = []
#1.여기서 크롤링을해서 dictionary_list로 값을 다 넣은다음에 

#0은 그대로, 1은 price 변경, 2는 플랫폼 다르게, 4 5는 title 다르게
data0 = {'DATE' : '2023.10.05(목)', 'TITLE' : '소드 아트 온라인 라스트 리콜렉션 한글판 (PS5, 패키지디스크)', 'PLATFORM' : 'PS5', 'PRICE' : '66,020'}
data1 = {'DATE' : '2023.10.05(목)', 'TITLE' : '소드 아트 온라인 라스트 리콜렉션 한글판 (PS5, 콜렉터즈에디션/패키지디스크)', 'PLATFORM' : 'PS5', 'PRICE' : '100,000'}
data2 = {'DATE' : '2023.10.05(목)', 'TITLE' : '어쌔신 크리드: 미라지 한글판 (PS5, 패키지디스크)', 'PLATFORM' : 'Switch', 'PRICE' : '66,020'}
data3 = {'DATE' : '2023.10.05(목)', 'TITLE' : '소드 아트 온라인', 'PLATFORM' : 'PS5', 'PRICE' : '66,020'}
data4 = {'DATE' : '2023.10.05(목)', 'TITLE' : '액셀월드', 'PLATFORM' : 'PS5', 'PRICE' : '66,020'}
dictionary_list.append(data0)
dictionary_list.append(data1)
dictionary_list.append(data2)
dictionary_list.append(data3)
dictionary_list.append(data4)

#2.여기서 딕셔너리의 title값을 하나씩 가져와서 insert나 update문으로 db 처리
for data in dictionary_list:
    date = data['DATE']
    title = data['TITLE']
    platform = data['PLATFORM']
    price = data['PRICE']
    # 데이터베이스에 해당 TITLE이 존재하는지 확인
    query = "SELECT * FROM release_info WHERE TITLE = %s AND platform = %s"
    cursor.execute(query, (title, platform))
    result = cursor.fetchone()

    if result is None:
        # 데이터베이스에 존재하지 않으면 INSERT
        insert_query = "INSERT INTO release_info (DATE, TITLE, PLATFORM, PRICE, VARIA) VALUES (%s, %s, %s, %s, %s)"
        cursor.execute(insert_query, (date, title, platform, price, int(1)))
        conn.commit()
        print(f"INSERT: {title}")
    else:
        # 데이터베이스에 이미 존재하면 price를 비교하여 업데이트하고 varia 값을 1로 변경
        existing_price = result[3]  # 결과에서 현재 데이터베이스의 price 가져오기
        if existing_price != price:
            update_query = "UPDATE release_info SET PRICE = %s, VARIA = 1 WHERE TITLE = %s AND PLATFORM = %s"
            cursor.execute(update_query, (price, title, platform))
            conn.commit()
            print(f"UPDATE: {title} (Price || Varia Updated)")
        else:
            print(f"UPDATE: {title} (Price Not Changed)")

#varia값 변경이 전부끝났으면 0은 전부 삭제
delete_query = "DELETE FROM release_info WHERE VARIA = 0"
cursor.execute(delete_query)
conn.commit()

if conn:
    cursor.close()
    conn.close()