package com.photo.web.dto.auth;

import com.photo.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupReqDto {

    @Size(min = 2, max = 20)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }

    @Builder
    public SignupReqDto(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
