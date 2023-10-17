   document.getElementById("btn_write").addEventListener("click", function () {
        // iframe의 src 속성을 변경하여 /game/bbs_write 페이지를 표시
        var iframe = document.querySelector(".change_frame");
        iframe.setAttribute("src", "/bbs_write");       
    });
    var currentURL = window.location.href;
    document.getElementById("btn_new").addEventListener("click", function () {
        // iframe의 src 속성을 변경하여 /game/bbs_write 페이지를 표시
        var iframe = document.querySelector(".change_frame");
        iframe.setAttribute("src", "/new");       
    });
    document.getElementById("btn_popular").addEventListener("click", function () {
        // iframe의 src 속성을 변경하여 /game/bbs_write 페이지를 표시
        var iframe = document.querySelector(".change_frame");
        iframe.setAttribute("src", "/views");       
    });
    document.getElementById("btn_search").addEventListener("click", function () {
        // iframe의 src 속성을 변경하여 /game/bbs_write 페이지를 표시
        var iframe = document.querySelector(".change_frame");
        iframe.setAttribute("src", "/list/search");       
    });
    // 페이지가 로드될 때 실행되는 코드
    /*
	var buttonContainer1 = document.querySelector('.button-container1');
    buttonContainer1.style.color = 'black'; // 텍스트 색상
    buttonContainer1.style.fontSize = '33px'; // 글꼴 크기
    */

 
    

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