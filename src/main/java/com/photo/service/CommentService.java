package com.photo.service;

import com.photo.domain.comment.Comment;
import com.photo.domain.comment.CommentRepository;
import com.photo.domain.image.Image;
import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import com.photo.handler.exception.CustomApiException;
import com.photo.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment comment(CommentDto commentDto, int sessionId){

        User userEntity = userRepository.findById(sessionId).orElseThrow(
            new Supplier<IllegalArgumentException>() {

                @Override
                public IllegalArgumentException get() {
                    return new IllegalArgumentException("사용자 아이디를 찾을 수 없습니다.");
                }
            });

        Image image = new Image();
        image.setId(commentDto.getImageId());

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .user(userEntity)
                .image(image)
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public void DeleteComment(int id){
        try{
            commentRepository.deleteById(id);
        }catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }

}
