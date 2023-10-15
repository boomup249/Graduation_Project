import importlib
import csv
from time import sleep
import os
import re

def install_and_import(package):
    try:
        importlib.import_module(package)
    except ImportError:
        import subprocess
        print(package, " 설치중...")
        subprocess.check_call(["pip", "install", package])
        sleep(2)
        print(package, " 설치 완료")

print("필수 라이브러리 확인중")
try:
    import MySQLdb
except ImportError:
    print("mysqlclient 미설치 확인 설치 진행")
    install_and_import("mysqlclient")

try:
    conn = MySQLdb.connect(
        user="root",
        passwd="1937",
        host="localhost",
        db="member"
    )
except:
    print("DB연결 실패, DB 실행상태 또는 비밀번호를 확인하세요")

cursor = conn.cursor()

cursor.execute("DROP TABLE IF EXISTS gamedata_switch_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_ps_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_steam_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_epic_genre")
cursor.execute("DROP TABLE IF EXISTS gamedata_switch")
cursor.execute("DROP TABLE IF EXISTS gamedata_ps")
cursor.execute("DROP TABLE IF EXISTS gamedata_steam")
cursor.execute("DROP TABLE IF EXISTS gamedata_epic")
cursor.execute("DROP TABLE IF EXISTS release_info;")

cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_switch (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                            `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                            `IMGDATA` TEXT NULL DEFAULT NULL,
                                                            `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                            `URL` TEXT NULL DEFAULT NULL,
                                                            `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                            PRIMARY KEY (`NUM`),
                                                            UNIQUE KEY (`TITLE`))
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_switch_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                    `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                    `GENRE` VARCHAR(30) NULL DEFAULT NULL,
                                                                    PRIMARY KEY (`NUM`),
                                                                    CONSTRAINT `switch_title`
                                                                        FOREIGN KEY (`TITLE`)
                                                                        REFERENCES `gamedata_switch` (`TITLE`)
                                                                        ON DELETE CASCADE
                                                                        ON UPDATE CASCADE)
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_ps (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                        `TITLE` VARCHAR(200) NULL DEFAULT NULL,
                                                        `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                        `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                        `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                        `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                        `IMGDATA` TEXT NULL DEFAULT NULL,
                                                        `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                        `URL` TEXT NULL DEFAULT NULL,
                                                        `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                        PRIMARY KEY (`NUM`),
                                                        UNIQUE KEY (`TITLE`))
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_ps_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(200) NULL DEFAULT NULL,
                                                                `genre` VARCHAR(30) NULL DEFAULT NULL,
                                                                PRIMARY KEY (`NUM`),
                                                                CONSTRAINT `ps_title`
                                                                    FOREIGN KEY (`TITLE`)
                                                                    REFERENCES `gamedata_ps` (`TITLE`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE)
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_steam (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                            `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                            `IMGDATA` TEXT NULL DEFAULT NULL,
                                                            `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                            `URL` TEXT NULL DEFAULT NULL,
                                                            `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                            PRIMARY KEY (`NUM`),
                                                            UNIQUE KEY (`TITLE`))
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_steam_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                                `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                                `genre` VARCHAR(30) NULL DEFAULT NULL,
                                                                PRIMARY KEY (`NUM`),
                                                                CONSTRAINT `steam_title`
                                                                    FOREIGN KEY (`TITLE`)
                                                                    REFERENCES `gamedata_steam` (`TITLE`)
                                                                    ON DELETE CASCADE
                                                                    ON UPDATE CASCADE)
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_epic (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                            `SALEPER` VARCHAR(5) NULL DEFAULT NULL,
                                                            `DESCRIPTION` TEXT NULL DEFAULT NULL,
                                                            `IMGDATA` TEXT NULL DEFAULT NULL,
                                                            `GAMEIMG` TEXT NULL DEFAULT NULL,
                                                            `URL` TEXT NULL DEFAULT NULL,
                                                            `VARIA` TINYINT(1) NOT NULL DEFAULT 1,
                                                            PRIMARY KEY (`NUM`),
                                                            UNIQUE KEY (`TITLE`))
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS gamedata_epic_genre (`NUM` INT NOT NULL AUTO_INCREMENT,
                                                            `TITLE` VARCHAR(100) NULL DEFAULT NULL,
                                                            `GENRE` VARCHAR(30) NULL DEFAULT NULL,
                                                            PRIMARY KEY (`NUM`),
                                                            CONSTRAINT `epic_title`
                                                                FOREIGN KEY (`TITLE`)
                                                                REFERENCES `gamedata_epic` (`TITLE`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE CASCADE)
            ''')
cursor.execute('''CREATE TABLE IF NOT EXISTS release_info (`DATE` DATE NULL DEFAULT NULL,
										                   `TITLE` VARCHAR(200) NOT NULL,
										                   `PLATFORM` VARCHAR(15) NULL DEFAULT NULL,
										                   `PRICE` VARCHAR(15) NULL DEFAULT NULL,
                                                           `ETC` VARCHAR(15) NULL DEFAULT NULL,
                                                           `VARIA` TINYINT(1) NULL DEFAULT 1,
                                                           PRIMARY KEY (`TITLE`));
            ''')

def insert_gamedata(platform, filename1, filename2):
    # 현재 스크립트 파일의 디렉토리 경로를 가져옵니다.
    script_dir = os.path.dirname(__file__)
    # 파일 이름을 포함하여 파일의 절대 경로를 생성합니다.
    file_name1 = filename1
    csv_file1= os.path.join(script_dir, file_name1)

    file_name2 = filename2
    csv_file2= os.path.join(script_dir, file_name2)

    try:
        with open(csv_file1, 'r', encoding='utf-8') as file:
            csv_reader = csv.reader(file)
            next(csv_reader) # 첫번째 행은 헤더라 건너뜀
            for row in csv_reader:
                # CSV 파일 데이터를 gamedata_epic 테이블에 삽입
                if platform == 'epic':
                    insert_query = """
                    INSERT INTO gamedata_epic (TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL)
                    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                    """
                elif platform == "steam":
                    insert_query = """
                    INSERT INTO gamedata_steam (TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL)
                    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                    """
                elif platform == "ps":
                    insert_query = """
                    INSERT INTO gamedata_ps (TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL)
                    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                    """
                elif platform == "switch":
                    insert_query = """
                    INSERT INTO gamedata_switch (TITLE, PRICE, SALEPRICE, SALEPER, DESCRIPTION, IMGDATA, GAMEIMG, URL)
                    VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                    """
                try:
                    cursor.execute(insert_query, tuple(row))
                except Exception as e:
                    print(f'{platform}.게임데이터 오류 - {e}')

        conn.commit()
        print(f"{platform} 데이터 삽입이 완료되었습니다.")

    except Exception as e:
        print(f"{platform} 오류 발생:", e)

    try:
        with open(csv_file2, 'r', encoding='utf-8') as file:
            csv_reader = csv.reader(file)
            next(csv_reader) # 첫번째 행은 헤더라 건너뜀
            for row in csv_reader:
                # CSV 파일 데이터를 gamedata_epic 테이블에 삽입
                if platform == 'epic':
                    insert_query = """
                    INSERT INTO gamedata_epic_genre (TITLE, GENRE)
                    VALUES (%s, %s)
                    """
                elif platform == "steam":
                    insert_query = """
                    INSERT INTO gamedata_steam_genre (TITLE, GENRE)
                    VALUES (%s, %s)
                    """
                elif platform == "ps":
                    insert_query = """
                    INSERT INTO gamedata_ps_genre (TITLE, GENRE)
                    VALUES (%s, %s)
                    """
                elif platform == "switch":
                    insert_query = """
                    INSERT INTO gamedata_switch_genre (TITLE, GENRE)
                    VALUES (%s, %s)
                    """
                try:
                    cursor.execute(insert_query, tuple(row))
                except Exception as e:
                    #print(f'장르 : {e}')
                    pass

        conn.commit()
        print(f"{platform} genre 데이터 삽입이 완료되었습니다.")

    except Exception as e:
        #print(f"{platform} genre 오류 발생:", e)
        pass

def insert_danawa(filename):
    # 현재 스크립트 파일의 디렉토리 경로를 가져옵니다.
    script_dir = os.path.dirname(__file__)
    # 파일 이름을 포함하여 파일의 절대 경로를 생성합니다.
    file_name = filename
    csv_file= os.path.join(script_dir, file_name)

    try:
        with open(csv_file, 'r', encoding='utf-8') as file:
            csv_reader = csv.reader(file)
            next(csv_reader) # 첫번째 행은 헤더라 건너뜀
            for row in csv_reader:
                date_value = None if row[0] == '' else row[0]
                insert_query = """
                INSERT INTO release_info (`DATE`, `TITLE`, `PLATFORM`, `PRICE`, `ETC`)
                VALUES (%s, %s, %s, %s, %s)
                """
                cursor.execute(insert_query, (date_value, row[1], row[2], row[3], row[4]))

        conn.commit()
        print("danawa 데이터 삽입이 완료되었습니다.")

    except Exception as e:
        print("danawa 오류 발생:", e)

if __name__ == "__main__":
    insert_gamedata('epic', 'Epic_Crawling.csv', 'Epic_Genre_Crawling.csv')
    sleep(2)
    insert_gamedata('steam', 'Steam_Crawling.csv', 'Steam_Genre_Crawling.csv')
    sleep(2)
    insert_gamedata('ps', 'PS_Crawling.csv', 'PS_Genre_Crawling.csv')
    sleep(2)
    insert_gamedata('switch', 'Switch_Crawling.csv', 'Switch_Genre_Crawling.csv')
    sleep(2)
    insert_danawa('Danawa_Crawling.csv')

    if conn:
        cursor.close()
        conn.close()
        print("DB연결 종료")