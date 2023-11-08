var ch_prefer = document.getElementById("ch_prefer");
var ch_prefer1 = document.getElementById("ch_prefer1");


function PreferChange(Mapping){
	var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	var values = [];
	for (var i = 0; i < checkboxes.length; i++) {
	    if (checkboxes[i].checked) {
	        values.push(checkboxes[i].value);
	    }
	}
	var xhr = new XMLHttpRequest();
	xhr.open('POST', Mapping, true);
	xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
	
	var data = {
	    genre: values
	};
	
	xhr.onload = function () {
	    if (xhr.status === 200) {
	        console.log(data);
	    } else {
	        console.log('저장 실패');
	    }
	};
	
	xhr.send(JSON.stringify(data));
    setTimeout(function () {
    location.reload();
    }, 1000);
};ch_prefer


//dislike 체크박스 비활성화
var dislike_checkboxes = document.querySelectorAll('#checktableDislike_td input[type="checkbox"]');
//이전 폼에서 like된 체크박스
var select_ck = document.getElementsByName('Love');
var sc_value = select_ck[0].value; //value

var value = sc_value.split(',');//,기준으로 나누어 배열에 넣기

for(var i=0; i < value.length; i++){//
   for(var j=0; j < dislike_checkboxes.length; j++){//
      if(value[i] == dislike_checkboxes[j].value){
         dislike_checkboxes[j].disabled = true; // 선택된 체크박스를 비활성화
      }
   }
}


//like 체크박스 비활성화
var like_checkboxes = document.querySelectorAll('#checktableLike_td input[type="checkbox"]');
//이전 폼에서 like된 체크박스
var select_ck = document.getElementsByName('dislike');
var sc_value = select_ck[0].value; //value

var value = sc_value.split(',');//,기준으로 나누어 배열에 넣기

for(var i=0; i < value.length; i++){//
   for(var j=0; j < like_checkboxes.length; j++){//
      if(value[i] == like_checkboxes[j].value){
         like_checkboxes[j].disabled = true; // 선택된 체크박스를 비활성화
      }
   }
}

/* 비밀번호 변경 */
var pwd_btn = document.getElementById("ch_pwd"); //비밀번호 수정버튼
var pwd = document.getElementById("pwd"); // 비밀번호 표시
var input_pwd = document.getElementById('input_pwd');//기존 비밀번호 div
var current_pwd = document.getElementById("current_pwd"); // 기존 비밀번호 input
var check_pwd = document.getElementById("check_pwd");//비밀번호 확인버튼
var span_ck = document.getElementById("ck"); //span
var ch_pwd_edit = document.getElementById("ch_pwd_edit"); //변경버튼
var newpwd = document.getElementById("newpwd"); //새 비밀번호 입력
var newpwd_re = document.getElementById("newpwd_re"); //새 비밀번호 확인
var new_ck = document.getElementById("new_ck");

/* 기존 비밀번호 입력 input 띄우기 */
pwd_btn.addEventListener("click",function(){
	input_pwd.style.visibility = "visible";
	pwd.style.visibility = "hidden";
});
/* 기존 비밀번호 체크 */
check_pwd.addEventListener("click", function(){
	var pwdStr = current_pwd.value;
	
	const user = {pwd : pwdStr}
	
	fetch("/api/checkPwd", {
		method: "POST",
		body: JSON.stringify(user),
		headers:{
			"Content-Type": "application/json"
		}
	})
	.then((response) => response.json())
	.then((json) => {
		if (json !== null){
			console.log(json);
		} else {
			alert("서버오류")
		}
		if(json === true){
			input_pwd.style.visibility = "hidden";
			edit_pwd.style.visibility = "visible";
			pwd_btn.style.visibility = "hidden";
			ch_pwd_edit.style.visibility = "visible";
			
		}else{
			span_ck.textContent = "비밀번호 불일치";
			span_ck.style.color = "red";
		}
	})
	
});

//newpwd입력

newpwd_re.addEventListener("input", function(event) {
	var newpwd_str = newpwd.value;
	var newpwdRe_str = newpwd_re.value;
	console.log(newpwd_str,newpwdRe_str);
    if (newpwd_str === newpwdRe_str) {
        new_ck.textContent = "비밀번호 일치";
        new_ck.style.color = "blue";
        ch_pwd_edit.disabled = false; // 비활성화 해제
    } else {
        new_ck.textContent = "비밀번호 불일치";
        new_ck.style.color = "red";
        ch_pwd_edit.disabled = true; // 버튼을 비활성화
    }
})

//변경버튼
ch_pwd_edit.addEventListener("click",function(){
	var newpwdRe_str = newpwd_re.value;
	var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/UpdatekPwd', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

    var data = {
        new_pw: newpwdRe_str
    };

    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log(data);
        } else {
            console.log('저장 실패');
        }
    };

    xhr.send(JSON.stringify(data));
    setTimeout(function () {
        location.reload();
    }, 500);
});


/* 장르 */
ch_prefer.addEventListener("click", function(){
	PreferChange('/api/genreLike');
});

ch_prefer1.addEventListener("click", function(){
	PreferChange('/api/genreHate');
});