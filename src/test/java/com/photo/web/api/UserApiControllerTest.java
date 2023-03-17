package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import com.photo.service.FollowService;
import com.photo.service.UserService;
import com.photo.web.dto.ResDto;
import com.photo.web.dto.follow.FollowInfoDto;
import com.photo.web.dto.user.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Mockito.*;

public class UserApiControllerTest {
    private UserService userService;
    private FollowService followService;
    private UserApiController userApiController;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        followService = mock(FollowService.class);
        userApiController = new UserApiController(userService, followService);
    }

    @Test
    @DisplayName("세션 사용자가 프로필 이미지를 업데이트할 때 성공 응답과 상태 OK를 반환하는 경우")
    public void testProfileImageUpdate() throws Exception {
        // given
        int sessionId = 1;
        MultipartFile profileImageFile = mock(MultipartFile.class);
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        User userEntity = new User();
        when(userService.profileImageUpdate(sessionId, profileImageFile)).thenReturn(userEntity);

        // when
        ResponseEntity<?> response = userApiController.profileImageUpdate(sessionId, profileImageFile, customUserDetails);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertThat(responseBody.getCode()).isEqualTo(1);
        assertThat(responseBody.getMessage()).isEqualTo("회원 프로필 사진 변경 성공");
        assertThat(responseBody.getData()).isNull();
        verify(customUserDetails, times(1)).setUser(userEntity);
    }


    @Test
    @DisplayName("페이지 사용자가 팔로우 목록을 요청할 때 성공 응답과 상태 OK를 반환하는 경우")
    public void testFollowList() {
        // given
        int pageUserId = 2;
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        User user = new User();
        user.setId(1); // set a mock user ID
        when(customUserDetails.getUser()).thenReturn(user);
        List<FollowInfoDto> followInfoDtoList = new ArrayList<>();
        when(followService.followInfoList(customUserDetails.getUser().getId(), pageUserId)).thenReturn(followInfoDtoList);

        // when
        ResponseEntity<?> response = userApiController.followList(pageUserId, customUserDetails);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertThat(responseBody.getCode()).isEqualTo(1);
        assertThat(responseBody.getMessage()).isEqualTo("구독자 정보 리스트 불러오기 성공");
        assertThat(responseBody.getData()).isEqualTo(followInfoDtoList);
    }


    @Test
    @DisplayName("사용자 업데이트 DTO가 주어졌을 때 사용자 정보를 업데이트하면 성공 응답과 상태 OK를 반환하는 경우")
    public void testUpdate() {
        // given
        int id = 3;
        UserUpdateDto userUpdateDto = mock(UserUpdateDto.class);
        BindingResult bindingResult = mock(BindingResult.class);
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        User userEntity = new User();
        when(userService.userUpdate(id, userUpdateDto.toEntity())).thenReturn(userEntity);

        // when
        ResDto<?> response = userApiController.update(id, userUpdateDto, bindingResult, customUserDetails);

        // then
        assertThat(response.getCode()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("회원 수정 완료");
        assertThat(response.getData()).isEqualTo(userEntity);
        verify(customUserDetails, times(1)).setUser(userEntity);
    }
}
