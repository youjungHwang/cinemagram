package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import com.photo.handler.exception.CustomValidationApiException;
import com.photo.handler.exception.CustomValidationException;
import com.photo.service.UserService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public ResDto<?> update(@PathVariable int id,
                            @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(bindingResult.hasErrors()) {
            Map<String,String> errors = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사에 실패하였습니다.", errors);
        }else {
            User userEntity = userService.userUpdate(id, userUpdateDto.toEntity());
            customUserDetails.setUser(userEntity);
            return new ResDto<>(1,"회원 수정 완료", userEntity);
        }
    }
}
