   document.getElementById("btn_write").addEventListener("click", function () {
        // iframe의 src 속성을 변경하여 /game/bbs_write 페이지를 표시
        var iframe = document.querySelector(".change_frame");
        iframe.setAttribute("src", "/bbs_write");       
    });
    
    document.addEventListener("DOMContentLoaded", function () {
    // 페이지가 로드될 때 실행되는 코드
    var buttonContainer1 = document.querySelector('.button-container1');
    buttonContainer1.innerHTML 
    = '<span style="font-size: 30px;">[소식]</span><br><span style="font-size: 18px;">  LOCO의 새로운 소식을 만나보세요.</span>';
    buttonContainer1.style.color = 'black'; // 텍스트 색상
    buttonContainer1.style.fontSize = '33px'; // 글꼴 크기
});
    
    // 공지사항 버튼 클릭 시 실행될 함수
    document.getElementById('btn_notice').addEventListener('click', function() {
        // button-container1 요소를 찾아서 내부 HTML을 '공지사항'으로 설정
        var buttonContainer1 = document.querySelector('.button-container1');
        buttonContainer1.innerHTML = '[공지사항]';
        buttonContainer1.style.color = 'black'; // 텍스트 색상
    	buttonContainer1.style.fontSize = '33px'; // 글꼴 크기
    });
    
    document.getElementById('btn_free').addEventListener('click', function() {
        // button-container1 요소를 찾아서 내부 HTML을 '공지사항'으로 설정
        var buttonContainer1 = document.querySelector('.button-container1');
        buttonContainer1.innerHTML = '[자유게시판]';
        buttonContainer1.style.color = 'black'; // 텍스트 색상
    	buttonContainer1.style.fontSize = '33px'; // 글꼴 크기
    });
    
    document.getElementById('btn_guide').addEventListener('click', function() {
        // button-container1 요소를 찾아서 내부 HTML을 '공지사항'으로 설정
        var buttonContainer1 = document.querySelector('.button-container1');
        buttonContainer1.innerHTML = '[게임 공략]';
        buttonContainer1.style.color = 'black'; // 텍스트 색상
    	buttonContainer1.style.fontSize = '33px'; // 글꼴 크기
    });
    
    document.getElementById('btn_party').addEventListener('click', function() {
        // button-container1 요소를 찾아서 내부 HTML을 '공지사항'으로 설정
        var buttonContainer1 = document.querySelector('.button-container1');
        buttonContainer1.innerHTML = '[파티원]';
        buttonContainer1.style.color = 'black'; // 텍스트 색상
    	buttonContainer1.style.fontSize = '33px'; // 글꼴 크기
    });
        /*
        document.addEventListener("DOMContentLoaded", function(){
         var buttons = document.querySelectorAll(".button");
         var iframe = document.querySelector(".change_frame");
         
         function handleButtonClick(event){
            var buttonId = event.target.id;
            
            if(buttonId == "btn_write")
            iframe.src ="/bbs_write"
            else if(buttonId == "btn_notice")
            content = "window.locaton.href='/bbs_notice'";
            iframe.src ="/bbs_notice"
            
         }
         
         buttons.forEach(function(button){
            button.addEventListener("click", handleButtonClick);
         });
      });
      */