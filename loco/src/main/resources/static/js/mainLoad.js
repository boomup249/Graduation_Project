document.addEventListener('DOMContentLoaded', function() {
  
  //
  var logo = document.getElementById("b_logo");
 
  var intro = document.getElementById("intro");
  var btnGo = document.getElementById("btn_go");
  
  //로고 애니메이션 실행
  logo.style.animation = "logo_animation 1s ease"; //한번만 실행, 로고 opacity 조절
  
  //인트로 애니메이션 실행
  logo.addEventListener("animationend", function() { //로고 애니메이션 끝나면 실행
	  intro.classList.remove("hidden");
	  intro.style.animation = "intro_animation 1s ease-in"; //한번만 실행, 인트로 메시지 opacity 조절
  });
  
  //버튼 애니메이션 실행
  intro.addEventListener("animationend", function() {
	  btnGo.style.animation = "btnG_animation 2s ease-in infinite"; //계속 실행, 버튼 깜빡이게
	  btnGo.classList.remove("hidden");
  })
  
  
});