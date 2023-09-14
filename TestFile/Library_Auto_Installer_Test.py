import importlib

from time import sleep
def install_and_import(package):
    try:
        importlib.import_module(package)
    except ImportError:
        import subprocess
        print(package, " installing...")
        subprocess.check_call(["pip", "install", package])
        sleep(2)

try:
    import requests
except ImportError:
    install_and_import("requests")

try:
    from bs4 import BeautifulSoup
except ImportError:
    install_and_import("bs4")

try:
    import selenium
except ImportError:
    install_and_import("selenium")

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import NoSuchElementException

try:
    from webdriver_manager.chrome import ChromeDriverManager
except ImportError:
    install_and_import("webdriver-manager")

try:
    import MySQLdb
except ImportError:
    install_and_import("mysqlclient")

print("필수 라이브러리 인스톨 완료")