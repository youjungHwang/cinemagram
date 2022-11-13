package com.photo.web;

import com.photo.domain.user.User;
import com.photo.service.AuthService;
import com.photo.web.dto.auth.SignupReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor // final 필드를 DI할 때 사용
@Controller // 1. IoC에 등록 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    private final AuthService authService;


    @GetMapping("/auth/signin")
    public String signinForm() {
        return "/auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "/auth/signup";
    }

    /*[ 회원가입 진행 ]
    * 회원 정보 입력 후 가입 버튼 클릭-> @PostMapping("/auth/signup") -> 리턴 /auth/signin.html
    * */
    @PostMapping("/auth/signup")
    public String signup(SignupReqDto signupReqDto) {
        User user = signupReqDto.toEntity();
        authService.signup(user);
        return "/auth/signin";
    }
}
