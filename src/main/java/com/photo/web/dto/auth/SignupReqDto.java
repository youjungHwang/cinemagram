package com.photo.web.dto.auth;

import com.photo.domain.user.User;
import lombok.Data;

@Data
public class SignupReqDto {
    private String username;
    private String password;
    private String email;
    private String name;

    // 회원가입용 빌더패턴 (User모델에 @Builder)
    // 4개의 데이터를 기반으로 User 객체 만들고 .build()해서 User 리턴함
    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }

}
