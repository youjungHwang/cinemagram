package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import com.photo.handler.exception.CustomValidationApiException;
import com.photo.service.FollowService;
import com.photo.service.UserService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.follow.FollowInfoDto;
import com.photo.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final FollowService followService;


    @GetMapping("/api/user/{pageUserId}/follow")
    public ResponseEntity<?> followList(@PathVariable int pageUserId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<FollowInfoDto> followInfoDto = followService.followInfoList(customUserDetails.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new ResDto<>(1, "구독자 정보 리스트 불러오기 성공", followInfoDto), HttpStatus.OK);

    }

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
