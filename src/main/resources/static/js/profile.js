/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "언팔로우") {

    	    $.ajax({
    	        type: "delete",
    	        url: "/api/follow/" + toUserId,
    	        dataType: "json",
    	        success: function(res) {
    	            $(obj).text("팔로우");
                    $(obj).toggleClass("blue");
    	        },
    	        error: function(error) {
    	            if(error.data == null) {
                        alert(error.responseJSON.message);
                    } else {
                        alert(error.responseJSON.data);
                    }
    	        }
    	    });
    	} else if($(obj).text() === "팔로우") {

    	    $.ajax({
                type: "post",
                url: "/api/follow/" + toUserId,
                dataType: "json",
                success: function(res) {
                    $(obj).text("언팔로우");
                    $(obj).toggleClass("blue");
                },
                error: function(error) {
                    if(error.data == null) {
                        alert(error.responseJSON.message);
                    } else {
                        alert(error.responseJSON.data);
                    }
                }
    	    });
    	}
}


// (2) 구독자 정보 모달 보기
// 로그인 id를 안 받아 오는 이유 : UserApiController의 @AuthenticationPrincipal로 확인
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");

	$.ajax({
    	    type: "get",
    	    url: "/api/user/" + pageUserId + "/follow",
    	    dataType: "json",
    	    success: function(res) {
    	        res.data.forEach((user) => {
    	            let item = getSubscribeModalItem(user);

    	            $("#subscribeModalList").append(item);

    	        });
    	    },
    	    error: function(error) {
    	           if(error.data == null) {
                        alert(error.responseJSON.message);
                   } else {
                        alert(error.responseJSON.data);
                   }
    	        }
    	});
}

function getSubscribeModalItem(user) {
    let item =`<div class="subscribe__item" id="subscribeModalItem-${user.id}">
               	<div class="subscribe__img">
               		<img src="/upload/${user.profileImageUrl}" onerror="this.src='/images/defaultImage.png'"/>
               	</div>
               	<div class="subscribe__text">
               		<h2>${user.username}</h2>
               	</div>
               	<div class="subscribe__btn">`;

                if(!user.equalUserState) { // 동일 유저가 아닐때만 버튼이 만들어져야함
                    if(user.followState){ // 구독한 상태
                        item += `<button class="cta blue" onclick="toggleSubscribe(${user.id}, this)">언팔로우</button>`;
                    }else { // 구독 안 한 상태
                        item += `<button class="cta" onclick="toggleSubscribe(${user.id}, this)">팔로우</button>`;
                    }
                }

                item += `
               	</div>
               </div>`;

    return item;
}

// (3) 유저 프로파일 사진 변경
function profileImageUpload(pageUserId, sessionId) {

    if(pageUserId != sessionId) {
        alert("자신의 프로필 사진만 수정할 수 있습니다.")
        return;
    }

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버에 이미지 전송
        let profileImageForm = $("#userProfileImageForm")[0]; // form 태그 자체

        // FormData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다.
        let formData = new FormData(profileImageForm);        // form 태그가 들고 있는 값들만 담긴다

        $.ajax({
            type: "put",
            url: "/api/user/" + sessionId + "/profileImageUrl",
            data: formData,
            contentType: false, // default : x-www-form-urlencoded로 파싱되는 것을 방지(사진 전송 못 함)
            processData: false, // default : contentType을 false로 줬을 땐 QueryString 자동 설정되는 거 해제.
            enctype: "multipart/form-data",
            dataType: "json",
            success: function(res) {

                // 사진 전송 성공시 이미지 변경
                let reader = new FileReader();
                reader.onload = (e) => {
                    $("#userProfileImage").attr("src", e.target.result);
                }
                reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.

            },
            error: function(error) {
                console.log("프로필 사진 변경 오류", error);
            }
        });
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






