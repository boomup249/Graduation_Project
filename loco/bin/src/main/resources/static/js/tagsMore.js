document.addEventListener("DOMContentLoaded", function () {
  // 처음 5개 태그는 숨기기 x
  var VisibleTags = document.querySelectorAll(".tags");
  for (var i = 0; i < 5 && i < VisibleTags.length; i++) {
    VisibleTags[i].classList.remove("tags_hidden");
  }
});

// + 클릭시 실행 함수
function showMoreTags() {
    var hiddenTags = document.querySelectorAll(".tags_hidden");
    hiddenTags.forEach(function (tag) {
      tag.classList.remove("tags_hidden");
    });

    // + 숨기기
    var showMoreButton = document.getElementById("show_more_button");
    showMoreButton.style.display = "none";
}