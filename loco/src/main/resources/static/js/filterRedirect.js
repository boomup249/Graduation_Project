//
function redirect() {
	//select 객체 받아오기
	var site = document.getElementById("site_filter").value;
	var order = document.getElementById("order_filter").value;
	
	//기본 url 찾기(여러 페이지에 적용하려고 직접 지정 안 함)
	var currentUrl = window.location.href;
    var baseUrl = currentUrl.split('?')[0]; // 현재 페이지의 기본 URl
    
    //필터 적용 url
    var plusUrl = '?site=' + site + '&orderby=' + order;
    
    if(order == 'pick'){
		plusUrl += '&category=0';
	} else { //pick이 아닌 경우
		
		// category_filter 엘리먼트가 존재한다면 해당 값을 추가
	    var categoryFilter = document.getElementById("category_filter");
	    if (categoryFilter) {
	        var category = categoryFilter.value;
	        plusUrl += '&category=' + category;
	    }
		
	}
    
    var newUrl = baseUrl + plusUrl;
    
    //새로운 url 리다이렉트
    window.location.href = newUrl;
}

// 선택값 변경 이벤트 리스너 추가
document.getElementById("site_filter").addEventListener("change", redirect);
document.getElementById("order_filter").addEventListener("change", redirect);

//category가 존재한다면
if(document.getElementById("category_filter")){
	document.getElementById("category_filter").addEventListener("change", redirect);
}