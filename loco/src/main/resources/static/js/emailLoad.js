document.addEventListener('DOMContentLoaded', function() {
  //
  var emailP = document.getElementById("content");
  
  //동작
  emailP.classList.remove("hidden"); //이메일 화면 보이게
  emailP.style.animation = "page_up_animation 2s ease";
  
});