package com.photo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.photo.domain.comment.Comment;
import com.photo.domain.comment.CommentRepository;
import com.photo.domain.image.Image;
import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import com.photo.handler.exception.CustomApiException;
import com.photo.web.dto.comment.CommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    private CommentService commentService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        commentRepository = mock(CommentRepository.class);
        commentService = new CommentService(commentRepository, userRepository);
    }

    @Test
    @DisplayName("댓글 등록 - 유저 아이디 존재")
    public void testCommentWithValidUserId() {
        // given
        int sessionId = 1;
        CommentDto commentDto = new CommentDto("댓글 내용", 1);

        User user = new User();
        user.setId(sessionId);

        Image image = new Image();
        image.setId(commentDto.getImageId());

        Comment expectedComment = Comment.builder()
                .content(commentDto.getContent())
                .user(user)
                .image(image)
                .build();

        when(userRepository.findById(sessionId)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(expectedComment);

        // when
        Comment result = commentService.comment(commentDto, sessionId);

        // then
        assertEquals(expectedComment.getContent(), result.getContent());
        assertEquals(expectedComment.getUser(), result.getUser());
        assertEquals(expectedComment.getImage(), result.getImage());
    }

    @Test
    @DisplayName("댓글 등록 - 유저 아이디 미존재")
    public void testCommentWithInvalidUserId() {
        // given
        int sessionId = 1;
        CommentDto commentDto = new CommentDto("댓글 내용", 1);
        when(userRepository.findById(sessionId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(IllegalArgumentException.class, () -> {
            commentService.comment(commentDto, sessionId);
        });
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void testDeleteComment() {
        // given
        int commentId = 1;

        // when
        doNothing().when(commentRepository).deleteById(commentId);
        commentService.DeleteComment(commentId);

        // then
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("댓글 삭제 예외 처리 테스트")
    public void testDeleteCommentException() {
        // given
        int commentId = 1;

        // when
        doThrow(new RuntimeException()).when(commentRepository).deleteById(commentId);

        // then
        assertThrows(CustomApiException.class, () -> commentService.DeleteComment(commentId));
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
