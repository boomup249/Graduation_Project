const main = {
    init : function() {
        const _this = this;
 
        
        // 댓글 저장
        $('#comm_save').on('click', function () {
            _this.commentSave();
        });
 
    },
    
    
     /** 댓글 저장 */
    commentSave : function () {
        const data = {
            postsId: $('#postsId').val(),
            comment: $('#comment').val()
        }
 
        // 공백 및 빈 문자열 체크
        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/comm/article/' + data.postsId + '/comments',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('댓글이 등록되었습니다.');
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
};
 
main.init();