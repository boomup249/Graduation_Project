const mailCheck = document.querySelector('#sub1');
 
mailCheck.addEventListener("click", () => {
    const email = document.querySelector('#userEmail').value;
    const Warn = document.getElementById('warn');
    const user = {
        username: email
    }
 
    const url = "email";
    fetch(url, {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then((response) => response.json())
        .then((json) => {
 
            if (json !== null) {
                Warn.textContent = "인증번호 발송이 완료되었습니다.";
                authNum = json;
                console.log(authNum);
            } else {
                Warn.textContent = "인증메일 전송에 실패 하였습니다.";
            }
        });
});

function checkAuthNumFn() {
    const mailCheckInput = document.querySelector('.mail-check-input').value;
    const mailCheckWarn = document.getElementById('warn');
 
    if (mailCheckInput != authNum) {
        mailCheckWarn.textContent = "인증번호가 다릅니다.";
        mailCheckWarn.style.color = 'red';
        return;
    } else {
        mailCheckWarn.textContent = "인증되었습니다.";
        mailCheckWarn.style.color = 'green';
        document.querySelector('#sub2').removeAttribute('disabled');
        authResult = true;
        return;
    }
}