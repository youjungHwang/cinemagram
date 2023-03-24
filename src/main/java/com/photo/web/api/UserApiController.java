package com.photo.web.api;

import com.photo.cache.UserRedisTemplateService;
import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import com.photo.service.AuthService;
import com.photo.service.FollowService;
import com.photo.service.UserService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.auth.SignupReqDto;
import com.photo.web.dto.follow.FollowInfoDto;
import com.photo.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserApiController {

    /*
    * 의존성 주입을 할때 final 삭제한 이유는 UserRedisTemplateService을 추가함으로써
    * UserApiControllerTest 시 setup 에러가 났음
    *
    * 쓰는 생성자만 따로 생성하는 것으로 코드 변경
    * */
    private  UserService userService; // final 삭제한 이유는 UserApiControllerTest 시 setup 에러, 쓰는 생성자만 따로 생성함
    private  FollowService followService;

    private  UserRedisTemplateService userRedisTemplateService; // [[ redis ]]

    public UserApiController(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }


    @PutMapping("/api/user/{sessionId}/profileImageUrl")
    public ResponseEntity<?> profileImageUpdate(@PathVariable int sessionId, MultipartFile profileImageFile,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User userEntity = userService.profileImageUpdate(sessionId, profileImageFile);
        customUserDetails.setUser(userEntity);
        return new ResponseEntity<>(new ResDto<>(1, "회원 프로필 사진 변경 성공", null), HttpStatus.OK);
    }


    @GetMapping("/api/user/{pageUserId}/follow")
    public ResponseEntity<?> followList(@PathVariable int pageUserId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<FollowInfoDto> followInfoDto = followService.followInfoList(customUserDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new ResDto<>(1, "구독자 정보 리스트 불러오기 성공", followInfoDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public ResDto<?> update(@PathVariable int id,
                            @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User userEntity = userService.userUpdate(id, userUpdateDto.toEntity());
        customUserDetails.setUser(userEntity);
        return new ResDto<>(1,"회원 수정 완료", userEntity);
    }

    /*
     * [[ redis ]]
     * DB에 있는 유저 데이터를 redis 에 동기화 처리
     * */
    @GetMapping("/api/redis/save")
    public String redisSave() {
        List<SignupReqDto> savedUserDtoList = userService.findAll()// userService.findAll()를 SignupReqDto 로 converting 해줌
                .stream().map(user -> SignupReqDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());

        savedUserDtoList.forEach(userRedisTemplateService::save);

        return "DB data synchronized to redis successfully";
    }

}
