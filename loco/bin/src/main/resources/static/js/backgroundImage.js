//이미지 링크 배열
var images = [
	      "imgs/background_imgs/APEX.jpg",
	      "imgs/background_imgs/battleground.png",
	      "imgs/background_imgs/callofduty.jpg",
	      "imgs/background_imgs/counterstrike.jpg",
	      "imgs/background_imgs/destiny.jpg",
	      "imgs/background_imgs/Halo.jpg",
	      "imgs/background_imgs/humanfallflat.jpg",
	      "imgs/background_imgs/mario.jpg",
	      "imgs/background_imgs/tekken.jpg",
	      "imgs/background_imgs/zelda.jpg"
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