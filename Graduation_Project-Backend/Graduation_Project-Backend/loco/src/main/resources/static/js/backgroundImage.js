//이미지 링크 배열
var images = [
	      "https://cdn.cloudflare.steamstatic.com/steam/apps/578080/header.jpg?t=1689138385",//PUBG : BG
	      "https://cdn.cloudflare.steamstatic.com/steam/apps/671860/header.jpg?t=1686877598", //BattleBit Remastered
	      "https://cdn.cloudflare.steamstatic.com/steam/apps/1049590/header_alt_assets_3.jpg?t=1689230639", //이터널 리턴
	      "https://cdn.cloudflare.steamstatic.com/steam/apps/1426210/header_koreana.jpg?t=1679951279", //It Takes Two
	      "https://cdn.cloudflare.steamstatic.com/steam/apps/381210/header.jpg?t=1687878531", //Dead by Daylight
	      "https://cdn.cloudflare.steamstatic.com/steam/apps/477160/header_alt_assets_1.jpg?t=1688117179" //Human: Fall Flat
	      ];
	      
//이전 인덱스와 겹칠 경우, 다시 랜덤하기 위함
var previousIndex = -1; //초기에 실제 index와 겹칠 일 없게 음수로 둠
var currentIndex = -1;
        
//랜덤 인덱스 선택
function getRandomIndex() {
	var index = Math.floor(Math.random() * images.length);
	      
	if (index === previousIndex) {
		// 이미 이전에 선택된 이미지와 동일한 인덱스가 나왔다면 다시 랜덤 선택
        index = getRandomIndex();
        }
	      
	    return index;
	    }
	    
//백그라운드에 인덱스 넣기
function setBackground() {
	//div 객체
	var divElement = document.getElementById("b_img");
	      
	//url 설정
	currentIndex = getRandomIndex();
	var imageUrl = images[currentIndex];
	divElement.style.backgroundImage = "url('" + imageUrl + "')";
	previousIndex = currentIndex;
	}
	
// 초기 실행
setBackground();	


// 5초마다 setBackground 함수 실행
setInterval(setBackground, 5000);