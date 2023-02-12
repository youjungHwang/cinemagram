package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.comment.Comment;
import com.photo.service.CommentService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public Comment comment(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return commentService.comment(commentDto, customUserDetails.getUser().getId());
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.DeleteComment(id);
        return new ResponseEntity<>(new ResDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }

}
