/* 아이디 */
var orgId = false;

function ChangeId(){
	var ch_id = document.getElementById("ch_id");
	var id_div = document.getElementById("id");
	var edit_id = document.getElementById("edit_id");
	if(!orgId){
		id_div.style.display = "none";
		edit_id.style.display = "block";
		ch_id.innerText = "변경";
		orgId = true;
	}
	else{
		id_div.style.display = "block";
		edit_id.style.display = "none";
		ch_id.innerText = "수정";
		orgId = false;
	}
}



/* 비밀 번호*/
var orgPwd = false;

function ChangePwd(){
	var ch_pwd = document.getElementById("ch_pwd");
	var pwd_div = document.getElementById("pwd");
	var edit_pwd = document.getElementById("edit_pwd");
	if(!orgPwd){
		pwd_div.style.display = "none";
  		edit_pwd.style.display = "block";
  		ch_pwd.innerText = "변경";
  		orgPwd = true;
	}
	else{
		pwd_div.style.display = "block";
  		edit_pwd.style.display = "none";
  		ch_pwd.innerText = "수정";
  		orgPwd =false;
	}
	
	
}

/* 이메일 */
var orgEmail = false;

function ChangeEmail(){
	var ch_email = document.getElementById("ch_email");
	var email_div = document.getElementById("email");
	var edit_email = document.getElementById("edit_email");
	
	if(!orgEmail){
		email_div.style.display = "none";
  		edit_email.style.display = "block";
  		ch_email.innerText = "변경";
  		orgEmail = true;
	}
	else{
		email_div.style.display = "block";
  		edit_email.style.display = "none";
  		ch_email.innerText = "수정";
  		orgEmail = false;
	}
	
}