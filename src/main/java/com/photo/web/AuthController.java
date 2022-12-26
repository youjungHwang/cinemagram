package com.photo.web;

import com.photo.domain.user.User;
import com.photo.handler.exception.CustomValidationException;
import com.photo.service.AuthService;
import com.photo.web.dto.auth.SignupReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;


    @GetMapping("/auth/signin")
    public String signinForm() {

        return "/auth/signin";
    }

    // @PostMapping 로그인 메서드는 스프링 시큐리티가 대신 처리하므로 직접 구현할 필요가 없다.

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "/auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupReqDto signupReqDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사에 실패하였습니다.", errors);
        }else {
            User user = signupReqDto.toEntity();
            authService.signup(user);
            return "/auth/signin";
        }
    }
}
