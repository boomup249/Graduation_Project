document.addEventListener("DOMContentLoaded", function () {
	var search_input = document.getElementById("search");
	var search_box = document.getElementById("search_box"); // div
	var search_btn = document.getElementById("search_submit");
	var search_Ul = document.getElementById("search_ul");
	let cache = "";

	// 타이머
	const timer = (beforeInput) => {
		// 0.5초마다 검색어를 확인
		// 하나하나 변경될때마다 데이터를 넘기는 것보다 딜레이가 있는것이 낫다 판단
		setTimeout(() => {
			if (search_input.value === beforeInput){
				loadData(search_input.value);
				checkInput();
			} else {
				checkInput();
			}
			
			// 검색어가 없으면 요소를 숨김
			if (search_input.value !== ""){
				search_box.style.display = "block";
			} else {
				search_box.style.display = "none";
			}
		}, 500);	
	}

	timer();
	
	// 일정 시간 간격으로 조회
	const checkInput = () => {
		const beforeInput = search_input.value;
		timer(beforeInput);
	}
	
	// 검색어 불러오기
	const loadData = (word) => {
		let url = `/api/search/${word}`;
		
		// 검색어를 입력하면 url 값이 변경된다
		if (cache !== url) {
			cache = url;
			let data = {};
			
			data.word = word;
			console.log(data); // {world = "축구"}
			
			fetch(cache,
			{
				method: "POST",
				headers: {
					"Content-Type": "application/json;",
				},
				body: JSON.stringify(data),
			}
			)
			//response 객체를 json 변환
			.then((res) => res.json())
			.then((data) => {
				console.log(data);
				fillSearch(data); // data로 list 만드는 함수 실행
			})
			.catch((err) => {
				console.log(err);
			});
		}
	} // loadData
	

	// 검색어 자동완성 리스트 채우기
	const fillSearch = (data) => {
		search_Ul.innerHTML = "";
		
		// 데이터 가공하기
		data.forEach((el, idx) => {
			const j_num = el.num;
			const j_title = el.title;
		    const j_saleprice = el.saleprice;
		    const j_imgdata = el.imgdata;
		    const j_site = el.siteavailability;
		    
		    //console.log(j_num , j_title , j_saleprice , j_imgdata)
		    // html 요소 생성
			const li = document.createElement("li");
			const link = document.createElement("a");
			
			const leftBox = document.createElement("div");
			const img = document.createElement("img");
			const title = document.createElement("span");
			
			//const rightBox = document.createElement("div");
			const price = document.createElement("span");
    		const hr = document.createElement("hr");
			
			leftBox.classList.add("left-box");
			title.innerHTML = j_title;
			img.setAttribute("src", j_imgdata);
			
			leftBox.appendChild(img);
			leftBox.appendChild(title);
			
			//rightBox.classList.add("right-box");
			
			if(j_site == "Switch" || j_site == "Ps")
				link.setAttribute("href", `/consoleDetail/${j_num}`);
			else if(j_site == "Steam" || j_site == "Epic")
				link.setAttribute("href", `/pcDetail/${j_num}`);
				
			li.appendChild(leftBox);
			li.style.border = '1px solid #000';
			
			link.appendChild(li);
			//li.appendChild(rightBox);
			
			search_Ul.appendChild(link);
			

			
		})

		searchUl.style.height = `${data.length * 30}px`;
	}//fillSearch
	
	
	
	

});
