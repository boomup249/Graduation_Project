document.addEventListener('DOMContentLoaded', function () {
	btn = document.getElementById('comm_save');
	btn.addEventListener('click', function () {
	    var postId = document.getElementById('postsId').value;
	    var comment = document.getElementById('comment').value;
	    var url = "/api/article/" + postId + "/comments";
	    
	    console.log(postId);
	    console.log(comment);
	    console.log(url);
	    
	    var data = {
        comment : comment
        };

	    var request = fetch(url, {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json;charset=UTF-8'
	        },
	        body: JSON.stringify(data)
	    });

	    request
	        .then(function (response) {
	            if (response.status === 200) {
		            location.reload();
	                console.log('요청 성공');
	            } else {
	                console.log('요청 실패');
	            }
	        })
	        .catch(function (error) {
	            console.log('요청 실패: ' + error);
	        });

	});
});