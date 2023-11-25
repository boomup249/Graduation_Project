# LOCO(통합 게임 플랫폼) 졸업 작품
스프링 부트 + python(크롤링) 게임 가격 비교 사이트
<br>
<br>

## 🖥프로젝트 소개
사이트 별 가격 비교를 위한 통합 게임 플랫폼입니다.
콘솔(닌텐도, playstation), PC(steam, epic games)로 구분하여 확인 가능하며, 신작 게임과 게시판도 사용 가능합니다.


## 🕰개발 기간
- 2023.04.05 ~ 2023.11.14


### 👤멤버 구성
- 팀장: 장혜민
  - **마이 페이지** - 프론트 & 백엔드
  - **검색창** - 프론트 & 백엔드
  - **PPT 및 보고서 작성**


- 조원: 김상훈
  - **크롤링**
  - 회원가입 -  **이메일 인증**
 
    
- 조원: 이용주
  - **게시판(BBS)** - 프론트엔드
 
    
- 조원: 류승균
  - **DataBase** 작성
  - **서버** 총괄
  - **게시판(BBS)** - 프론트 & 백엔드
  - 회원가입 - **비밀번호 암호화**
 
    
- 조원: 이주영
  - **신작 게임(News)** - 프론트 & 백엔드
  - **회원가입(생년월일/성별, 선호/비선호 장르 선택)** - 프론트 & 백엔드
  -  **콘솔/PC** - 게임 상세 페이지 백엔드
  -  **PPT 및 보고서 작성**
 
    
- 조원: 유지아
  - **Main** - 프론트 & 백엔드
  - **로그인** - 프론트 & 백엔드
  - **회원가입(총괄)** - 프론트 & 백엔드
  - **콘솔/PC** - 목록 페이지/상세 페이지 프론트 & 목록 페이지 백엔드
  - **PPT 및 보고서 작성**


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
### ① Main 

- background img 랜덤 전환
- 로고 애니메이션<br><br>
![main_1](https://github.com/boomup249/Graduation_Project/assets/100755594/3eaeb700-744e-464e-a689-c1a2a0427a6a)

<br>

- 화살표(↓) 버튼 누를 시, 상황에 따른 화면 전환 <br><br>
    **if )** <br>
    ⑴ 로그인이 되어있지 않은 경우 - 이메일 입력 화면으로 전환. 이미 존재하는 이메일이라면 로그인 화면으로, 존재하지 않는 이메일이라면 회원가입 화면으로 전환. <br><br>
    ![main_2](https://github.com/boomup249/Graduation_Project/assets/100755594/17f24e10-2530-43b1-a6e7-137afc781b8b)
  
    ⑵ 로그인이 되어있는 경우 - CONSOLE 화면으로 전환 <br><br>
      ![main_3](https://github.com/boomup249/Graduation_Project/assets/100755594/68f1ec33-d262-47ea-9955-f9b0f6cef4ff)
  
<br><br>


### ② 회원가입

- 이메일 인증 <br><br>
![email](https://github.com/boomup249/Graduation_Project/assets/100755594/b243c5b4-0052-40b9-bd9a-58096a3319f3)

<br>

- 아이디 중복 확인 <br><br>
  ![id](https://github.com/boomup249/Graduation_Project/assets/100755594/3fabefbe-8be6-49cd-b7b9-089091356657)

<br>

- 비밀번호 검증 <br><br>
  ![pwd](https://github.com/boomup249/Graduation_Project/assets/100755594/a516b785-e68b-41c9-8dae-0a983ef820ba)


<br>
  
- 생년월일 및 성별 선택 <br><br>
 ![info](https://github.com/boomup249/Graduation_Project/assets/100755594/a77ac85e-9cc0-4524-b39a-fdd0b8d8e501)


<br>

- 선호/비선호 장르 선택 <br><br>
  ![like dislike](https://github.com/boomup249/Graduation_Project/assets/100755594/2c4284a0-e805-43be-93e9-bb627b072932)

<br><br>

  
### ③ 로그인
- 이메일 혹은 아이디, 비밀번호 입력 후 로그인
- 가입하지 않은 사용자를 위한 회원가입 링크 <br><br>
![login](https://github.com/boomup249/Graduation_Project/assets/100755494/afb9931a-f591-4813-8a75-a3723ea9f62a)

<br><br>



### ④ 게임 목록(Console/PC)
- 크롤링한 정보(게임 대표 이미지, 가격, 마지막으로 크롤링 한 시간) 표시
- 사이트, 장르 필터링
- 순서(인기순, 할인순, 추천순) 정렬 **(※ 추천순은 로그인을 해야만 가능)** <br><br>
  ![gamelist_filter](https://github.com/boomup249/Graduation_Project/assets/100755594/daebc833-9997-479a-9a06-c15610bd138d)


<br>

- PC 게임의 경우, 두 사이트에 동시에 존재하는 게임이면 두 사이트 중 최저가를 표시
- 해당 게임이 존재하는 사이트의 로고를 모두 표시 <br><br>
  ![gamelist](https://github.com/boomup249/Graduation_Project/assets/100755594/da683168-c353-46ff-9a81-52fe72505ce6)

<br><br>


### ⑤ 게임 상세 페이지(Console/PC)
- 크롤링한 정보 표시(게임 제목, 대표 이미지, 서브 이미지, description) 표시
- 판매 사이트 및 가격 표시
- 판매 사이트 바로가기
- 장르 태그(#) 표시 <br><br>
![game_detail](https://github.com/boomup249/Graduation_Project/assets/100755594/86fdea88-786e-442f-b4ac-ccb8141f8b20)

<br><br>



### ⑥ 게시판(BBS)
- 카테고리 별 구분(공지사항, 자유게시판, 공략, 파티원)
- 최신글/인기글 순 정렬 <br><br>
![bbs](https://github.com/boomup249/Graduation_Project/assets/100755594/050d3ee7-6e53-4a2f-b8da-d28b3bb3f7c8)

<br>

- 글 작성(카테고리 선택, 제목, 글 내용 작성) <br><br>
![bbs_write](https://github.com/boomup249/Graduation_Project/assets/100755594/163f00df-6ff2-4c6e-b416-703789f49047)

<br>

- 글(제목) 검색 <br><br>
![bbs_search](https://github.com/boomup249/Graduation_Project/assets/100755594/87ad2118-45e3-4d3b-8c3f-422bcdc03c6e)

<br>

- 게시글에 댓글 작성 <br><br>
![bbs_comment](https://github.com/boomup249/Graduation_Project/assets/100755594/1e632723-6d20-44c3-b741-4b6681028460)

<br><br>


### ⑦ 신작 게임(News)
- 캘린더에 날짜별 출시 예정인 신작 게임을 표시
- 날짜가 확실하게 정해지지 않은 경우(ex. 202n년 출시 예정) 오른쪽 박스에 표시 <br><br>
![news](https://github.com/boomup249/Graduation_Project/assets/100755594/4e42960a-3e38-48f5-9f69-986e668463cc)

<br><br>



### ⑧ 마이 페이지
(1) 내 계정
  - 이메일, 아이디 표시
  - 비밀번호 수정 <br><br>
  ![myp_pwd](https://github.com/boomup249/Graduation_Project/assets/100755594/d6a0a666-5157-4313-ba9f-87b7ae695cb4)

  <br>
  
  - 선호/비선호 장르 수정 <br><br>
    ![myp_check](https://github.com/boomup249/Graduation_Project/assets/100755594/e9df3c04-46d4-439e-810b-f622923ee2fc)

    <br>

(2) 프로필
  - 프로필 이미지 설정
  - 닉네임, 한줄 소개 설정 <br><br>
  ![myp_profile](https://github.com/boomup249/Graduation_Project/assets/100755594/bf4a7a6e-dcbd-43f8-9cd0-bf137a226d60)

<br><br>


### ⑨ 검색창
- 드롭다운으로 검색한 게임 목록 표시
- 입력한 텍스트가 포함된 게임 전체를 자동완성하여 목록으로 표시(최대 10개)
- 검색 버튼을 누를 시, 전체 검색 결과 표시 <br><br>

![search](https://github.com/boomup249/Graduation_Project/assets/100755594/71cd1887-e88a-450f-8ca6-c726904de385)
