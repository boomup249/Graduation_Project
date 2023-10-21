var save_btn = document.getElementById("save_btn");
var input_nickname = document.getElementById("input_nickname"); //별명입력
var input_des = document.getElementById("input_des"); //소개입력

save_btn.addEventListener("click",function(){
	nickname_str = input_nickname.value;
	des_str = input_des.value;
	
    var data = {
        nickname: nickname_str,
        des: des_str
    };

    var request = fetch('/api/Uploadprofile', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(data)
    });

    request
        .then(function (response) {
            if (response.ok) {
                console.log('요청 성공');
            } else {
                console.log('요청 실패');
            }
        })
        .catch(function (error) {
            console.log('요청 실패: ' + error);
        });
});

//이미지 저장

var submit = document.getElementById("submit"); // 이미지 저장
var inputImg = document.getElementById("inputImg"); //이미지 파일 선택
submit.addEventListener("click",function(e){
	e.preventDefault();
	
    const files = inputImg.files;

    if (files.length === 0) {
        alert('파일을 선택하세요.');
        return;
    }

    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
        const fileNameWithoutExtension = files[i].name.replace(/\.[^.]+$/, "");
        
        formData.append('uploadedFiles', new File([files[i]], fileNameWithoutExtension, { type: files[i].type }));
    }
	console.log(formData);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/UploadImg', true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            alert('파일 업로드가 완료되었습니다.');
        } else {
            alert('파일 업로드 중 오류가 발생했습니다.');
        }
    };

    // FormData를 서버로 전송합니다.
    xhr.send(formData);
    setTimeout(function () {
	    location.reload();
    }, 100);
	
})