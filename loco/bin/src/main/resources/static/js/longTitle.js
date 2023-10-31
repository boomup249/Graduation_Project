document.addEventListener('DOMContentLoaded', function() {
  
  //타이틀 선택
	var titleTbElements = document.querySelectorAll('.title_tb');
	
	titleTbElements.forEach((element) => {
		var originalText = element.textContent;
	    element.dataset.originalText = originalText;
	
	    if (originalText.length > 50) {
			element.textContent = originalText.slice(0, 48) + '...';
			}
			
		element.addEventListener('mouseover', () => {
			element.textContent = element.dataset.originalText;
			
			if(element.textContent.length > 50) {
				element.style.width = '1100px';
			}
			
	        element.style.backgroundColor = 'gray';
	        });
	
	    element.addEventListener('mouseout', () => {
			if (element.textContent.length > 50) {
				element.textContent = element.textContent.slice(0, 48) + '...';
				element.style.removeProperty('width');
				}
			element.style.backgroundColor = '';
			});
		});
  
});