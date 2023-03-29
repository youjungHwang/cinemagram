package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.comment.Comment;
import com.photo.domain.user.User;
import com.photo.service.CommentService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.comment.CommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentApiControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentApiController commentApiController;

    private CommentDto testComment;

    @BeforeEach
    public void setUpEach() throws ServletException {
        testComment = CommentDto.builder()
                .content("댓글 남기고 갑니다.")
                .imageId(1L)
                .build();
    }

    @Test
    @DisplayName("유효한 CommentDto와 CustomUserDetails가 주어졌을 때, Comment를 반환한다.")
    public void givenValidCommentDtoAndCustomUserDetails_whenComment_thenReturnComment() {
        // given
        CommentDto commentDto = new CommentDto();

        CustomUserDetails customUserDetails = new CustomUserDetails(new User());
        when(commentService.comment(any(CommentDto.class), eq(customUserDetails.getUser().getId())))
                .thenReturn(new Comment());

        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        // when
        Comment result = commentApiController.comment(commentDto, bindingResult, customUserDetails);

        // then
        assertNotNull(result);
        verify(commentService, times(1)).comment(any(CommentDto.class), eq(customUserDetails.getUser().getId()));
    }

    @Test
    @DisplayName("유효한 CommentId가 주어졌을 때, CommentService의 DeleteComment가 호출된다.")
    public void givenValidCommentId_whenDeleteComment_thenCommentServiceDeleteCommentIsCalled() {
        // given
        Long id = 1L;

        // when
        commentApiController.deleteComment(id);

        // then
        verify(commentService, times(1)).DeleteComment(id);
    }

    @Test
    @DisplayName("유효한 CommentId가 주어졌을 때, 성공적인 Response를 반환한다.")
    public void givenValidCommentId_whenDeleteComment_thenReturnSuccessResponseEntity() {
        // given
        Long id = 1L;

        // when
        ResponseEntity<?> result = commentApiController.deleteComment(id);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof ResDto);
        ResDto<?> resDto = (ResDto<?>) result.getBody();
        assertEquals(1, resDto.getCode());
        assertEquals("댓글 삭제 성공", resDto.getMessage());
        assertNull(resDto.getData());
    }

    @Test
    @DisplayName("유효하지 않은 CommentId가 주어졌을 때, CommentService의 DeleteComment가 호출되지 않는다.")
    public void givenInvalidCommentId_whenDeleteComment_thenCommentServiceDeleteCommentIsNotCalled() {
        // given
        Long id = -1L;

        // when
        commentApiController.deleteComment(id);

        // then
        verify(commentService).DeleteComment(id);
    }
}
