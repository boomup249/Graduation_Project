   document.getElementById("btn_write").addEventListener("click", function () {
        // iframe의 src 속성을 변경하여 /game/bbs_write 페이지를 표시
        var iframe = document.querySelector(".change_frame");
        iframe.setAttribute("src", "/bbs_write");
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