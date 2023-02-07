package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.comment.Comment;
import com.photo.handler.exception.CustomValidationApiException;
import com.photo.service.CommentService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public Comment comment(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        if(bindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사에 실패하였습니다.", errors);
        }else {
            return commentService.comment(commentDto, customUserDetails.getUser().getId());
        }
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.DeleteComment(id);
        return new ResponseEntity<>(new ResDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }

}
