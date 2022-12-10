package com.photo.web;

import com.photo.domain.user.User;
import com.photo.handler.exception.CustomValidationException;
import com.photo.service.AuthService;
import com.photo.web.dto.auth.SignupReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
