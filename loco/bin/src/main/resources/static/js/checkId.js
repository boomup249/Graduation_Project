function checkId() {
	var uid = $('#uid').val(); //id=uid인 필드 값
	
	//길이 제한
	if (uid.length < 5 || uid.length > 15) {
        $('.msg2').css("display", "block");
        $('.msg1').css("display", "none");
        $('#sub').css("visibility", "hidden");
        return; // 길이가 범위를 벗어나면 검증 중단
    } else {
        $('.msg2').css("display", "none");
    }
	
	//디비에 있는지 확인(중복 확인)
	$.ajax({
		url: '/check_id',
		type: 'post',
		data: {id:uid},
		
		success:function(ck){
			if(ck == false){
				$('.msg1').css("display", "block");
				$('.msg3').css("display", "none");
				$('#sub').css("visibility", "visible");
			} else {
				$('.msg1').css("display", "none");
				$('.msg3').css("display", "block");
				$('#sub').css("visibility", "hidden");
			}
		},
		error:function(){
			alert("에러가 발생하였습니다.")
		}
	});
	
};