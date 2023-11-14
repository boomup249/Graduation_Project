const resend = document.getElementById("sub1");
const mailCheckInput = document.querySelector('#mail-check-input');
const mailCheckWarn = document.getElementById('warn');
const page = document.getElementById("sub2")

resend.addEventListener("click", () =>{
	const email = document.querySelector('#useremail').value;
	const user = {useremail : email}
	
	fetch("/api/email_auth", {
		method: "POST",
		body: JSON.stringify(user),
		headers:{
			"Content-Type": "application/json"
		}
	})
	.then((response) => response.json())
	.then((json) => {
		if (json !== null){
			mailCheckWarn.textContent = " 인증번호가 전송되었습니다.";
			authNum = json;
			//console.log(authNum);
		} else {
			alert("인증메일 전송에 실패했습니다.")
		}
	})
})


mailCheckInput.addEventListener("input", checkAuthNumFn);

function checkAuthNumFn() {
	const mailCheckValue = mailCheckInput.value;
 
    //console.log(mailCheckValue);
    //console.log(authNum);
 
    if (parseInt(mailCheckValue) !== parseInt(authNum)) {
        mailCheckWarn.textContent = " 인증번호가 다릅니다.";
        mailCheckWarn.style.color = 'red';
    } else {
        mailCheckWarn.textContent = " 인증되었습니다.";
        mailCheckWarn.style.color = 'blue';
        page.style.visibility="visible";
    }
}