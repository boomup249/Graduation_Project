import importlib
import schedule
from time import sleep

class Base_Setting():
    def install_and_import(package):
        try:
            importlib.import_module(package)
        except ImportError:
            import subprocess
            print(package, " 설치중...")
            subprocess.check_call(["pip", "install", package])
            sleep(2)

    def Library_Install(self):
        try:
            import requests
        except ImportError:
            self.install_and_import("requests")

        try:
            import bs4
        except ImportError:
            self.install_and_import("bs4")

        try:
            import selenium
        except ImportError:
            self.install_and_import("selenium")

        try:
            import webdriver_manager
        except ImportError:
            self.install_and_import("webdriver-manager")

        try:
            import MySQLdb
        except ImportError:
            self.install_and_import("mysqlclient")

        try:
            import schedule
        except ImportError:
            self.install_and_import("schedule")

        try:
            import multiprocessing
        except ImportError:
            self.install_and_import("multiprocessing")


    def __init__(self):
        print("필수 라이브러리 확인중")
        self.Library_Install()

    def __del__(self):
        print("필수 라이브러리 확인 완료")