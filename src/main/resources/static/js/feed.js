/**
	2. 피드 페이지
	(1) 피드 로드
	(2) 피드 스크롤 페이징
	(3) 좋아요, 좋아요 취소
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (1) 피드 로드
let page = 0;

function storyLoad() {
 $.ajax({
        type: "get",
        url: `/api/feed?page=${page}`,
        dataType: "json",
        success: function(res) {
            console.log(res.data)

            res.data.content.forEach((image) => {
                let storyItem = getStoryItem(image);

                $("#storyList").append(storyItem);
            });
        },
        error: function(error) {
            console.log("피드 렌더링 오류", error);
        }
    });
}

storyLoad();

function getStoryItem(image) {
    let item =`<div class="story-list__item">
               	<div class="sl__item__header">
               		<div>
               			<img class="profile-image" src="/upload/${image.user.profileImageUrl}" onerror="this.src='/images/defaultStory.png'" />
               		</div>
               		<div>${image.user.username}</div>
               	</div>

               	<div class="sl__item__img">
               		<img src="/upload/${image.imageUrl}" />
               	</div>

               	<div class="sl__item__contents">
               		<div class="sl__item__contents__icon">

               			<button>
               				<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>
               			</button>
               		</div>

               		<span class="like"><b id="storyLikeCount-1">3 </b>likes</span>

               		<div class="sl__item__contents__content">
               			<p>${image.caption}</p>
               		</div>

               		<div id="storyCommentList-1">

               			<div class="sl__item__contents__comment" id="storyCommentItem-1">
               				<p>
               					<b>blueberry :</b> 저도 꼭 볼게요!
               				</p>

               				<button>
               					<i class="fas fa-times"></i>
               				</button>

               			</div>

               		</div>

               		<div class="sl__item__input">
               			<input type="text" placeholder="댓글 달기" id="storyCommentInput-1" />
               			<button type="button" onClick="addComment()">게시</button>
               		</div>

               	</div>
                </div>`;
    return item;

}

// (2) 피드 스크롤 페이징
$(window).scroll(() => {
/*
    console.log("윈도우 현재 scroll 위치", $(window).scrollTop());
    console.log("전체 문서의 높이(고정값)", $(document).height());
    console.log("윈도우 높이(고정값)", $(window).height());
    결과 : 전체 문서의 높이 - 윈도우 높이 = 윈도우 현재 scroll 위치
*/

    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

    if(checkNum < 1 && checkNum > -1) {
        page++;
        storyLoad();
    }

});


// (3) 좋아요, 좋아요 취소
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) {
		likeIcon.addClass("fas");
		likeIcon.addClass("active");
		likeIcon.removeClass("far");
	} else {
		likeIcon.removeClass("fas");
		likeIcon.removeClass("active");
		likeIcon.addClass("far");
	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







