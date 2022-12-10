// (1) 회원정보 수정
function update(sessionId, event) {

        event.preventDefault();

        let data = $("#profileUpdate").serialize();

        $.ajax({
            type: "PUT",
            url: `/api/user/${sessionId}`,
            data: data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function(status) {
                location.href=`/user/${sessionId}`;
            },
            error: function(error,status) {
                if(error.responseJSON.data == null) {
                    alert(error.responseJSON.message);
                } else {
                    alert(JSON.stringify(error.responseJSON.data));
                }
            }
        });
}