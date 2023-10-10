document.addEventListener('DOMContentLoaded', function() {
  
   //장르 선택
	var titleTbElements = document.querySelectorAll('.genre_tb');
	
	titleTbElements.forEach((element) => {
		var originalText = element.textContent;
	    element.dataset.originalText = originalText;
	
	    if (originalText.length > 70) {
			element.textContent = originalText.slice(0, 68) + '...';
			}
			
		element.addEventListener('mouseover', () => {
			element.textContent = element.dataset.originalText;
			
			if(element.textContent.length > 70) {
				element.style.width = '1100px';
			}
			
	        element.style.backgroundColor = 'gray';
	        });
	
	    element.addEventListener('mouseout', () => {
			if (element.textContent.length > 70) {
				element.textContent = element.textContent.slice(0, 68) + '...';
				element.style.removeProperty('width');
				}
			element.style.backgroundColor = '';
			});
		});
  
});