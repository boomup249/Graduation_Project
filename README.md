## LOCO(통합 게임 플랫폼) 졸업 작품
스프링 부트 + python(크롤링) 게임 가격 비교 사이트


## 🖥프로젝트 소개
사이트 별 가격 비교를 위한 통합 게임 플랫폼입니다.
콘솔(닌텐도, playstation), PC(steam, epic games)로 구분하여 확인 가능하며, 신작 게임과 게시판도 사용 가능합니다.


## 🕰개발 기간
- 2023.04.05 ~ 2023.11.14


### 👤멤버 구성
- 팀장: 장혜민 - **마이 페이지** → 프론트 & 백엔드, **검색창** → 프론트 & 백엔드 , **PPT 및 보고서 작성**
- 조원: 김상훈 - **크롤링**, 회원가입 →  **이메일 인증** 
- 조원: 이용주 - **게시판(BBS)** → 프론트엔드
- 조원: 류승균 - **DataBase** 작성, **서버** 총괄, **게시판(BBS)** 구현, 회원가입 → **비밀번호 암호화**
- 조원: 이주영 - **신작 게임(News)** → 프론트 & 백엔드, **회원가입(생년월일/성별, 선호/비선호 장르 선택)** → 프론트 & 백엔드, **콘솔/PC** → 게임 상세 페이지 백엔드, **PPT 및 보고서 작성**
- 조원: 유지아 - **Main/로그인/회원가입** → 프론트 & 백엔드, **콘솔/PC** → 목록 페이지/상세 페이지 프론트 & 목록 페이지 백엔드, **PPT 및 보고서 작성**


### ⚙ 개발 환경
- Spring Framework
- JAVA
- HTML
- CSS
- Javascript
- ajax
- MySQL
- Python


## 📌 주요 기능
##### ① Main 
- background img 랜덤 전환
- 로고 애니메이션
- 화살표(↓) 버튼 누를 시, 상황에 따른 화면 전환
  if 1) 로그인이 되어있지 않은 경우 - 이메일 입력 화면으로 전환. 이미 존재하는 이메일이라면 로그인 화면으로, 존재하지 않는 이메일이라면 회원가입 화면으로 전환.
     2) 로그인이 되어있는 경우 - CONSOLE 화면으로 전환


##### ② 회원가입
- 이메일 인증
- 아이디 중복 확인
- 비밀번호 검증
- 생년월일 및 성별 선택
- 선호/비선호 장르 선택

