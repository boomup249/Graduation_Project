//like 체크박스 비활성화
			//폼의 전체 체크박스 선택
			var checkboxes = document.querySelectorAll('input[type="checkbox"]');
			//이전 폼에서 like된 체크박스
			var select_ck = document.getElementsByName('userLike');
			var sc_value = select_ck[0].value; //value
			
			var value = sc_value.split(',');//,기준으로 나누어 배열에 넣기
			
			for(var i=0; i < value.length; i++){//
				for(var j=0; j < checkboxes.length; j++){//
					if(value[i] == checkboxes[j].value){
						checkboxes[j].disabled = true; // 선택된 체크박스를 비활성화
					}
				}
			}