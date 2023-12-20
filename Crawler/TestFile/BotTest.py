import schedule
import time
from datetime import datetime

def run_daily_job():
    print("시간 출력")
    current_time = datetime.now()
    print("현재시간:", current_time.strftime("%H:%M:%S"))

# 스케줄러를 사용하여 1분마다 작업 예약
schedule.every(1).minutes.do(run_daily_job)

try:
    print("bot 실행 시작")
    while True:
        schedule.run_pending()
        pass
except KeyboardInterrupt:
    pass