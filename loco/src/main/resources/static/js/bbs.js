// JavaScript로 버튼 클릭 이벤트를 처리
    document.addEventListener("DOMContentLoaded", function () {
        // 버튼 요소들을 선택
        var buttons = document.querySelectorAll(".button");
        var buttonContainers = document.querySelectorAll("td.button-container1, td.button-container2");

        // 버튼 클릭 이벤트 핸들러
        function handleButtonClick() {
            // 모든 버튼에서 active-button 클래스 제거
            buttons.forEach(function (button) {
                button.classList.remove("active-button");
            });
            // 현재 클릭한 버튼에 active-button 클래스 추가
            this.classList.add("active-button");
        }

        // 각 버튼에 클릭 이벤트 리스너 등록
        buttons.forEach(function (button) {
            button.addEventListener("click", handleButtonClick);
        });

        // 버튼 컨테이너 중 하나만 활성화하도록 처리
        buttonContainers.forEach(function (container) {
            container.addEventListener("click", function () {
                // 모든 버튼 컨테이너에서 active-button 클래스 제거
                buttonContainers.forEach(function (buttonContainer) {
                    buttonContainer.classList.remove("active-button");
                });

                // 현재 클릭한 컨테이너에 active-button 클래스 추가
                container.classList.add("active-button");
            });
        });
    });
  
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