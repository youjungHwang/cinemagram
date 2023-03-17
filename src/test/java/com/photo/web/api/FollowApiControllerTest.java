package com.photo.web.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import com.photo.service.FollowService;
import com.photo.web.dto.ResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FollowApiControllerTest {

    private FollowService followService;
    private FollowApiController followApiController;

    @BeforeEach
    public void setUp() {
        followService = mock(FollowService.class);
        followApiController = new FollowApiController(followService);
    }

    @Test
    @DisplayName("유저 팔로우 성공시 성공 응답과 OK 상태 반환")
    public void testFollow() {
        // given
        int toUserId = 123;
        CustomUserDetails customUserDetails = new CustomUserDetails(new User());

        // when
        ResponseEntity<?> response = followApiController.follow(customUserDetails, toUserId);

        // then
        verify(followService, times(1)).follow(customUserDetails.getUser().getId(), toUserId);
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertAll("response",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(1, responseBody.getCode()),
                () -> assertEquals("팔로우 성공", responseBody.getMessage()),
                () -> assertNull(responseBody.getData()));
    }

    @Test
    @DisplayName("유저 언팔로우 성공시 성공 응답과 OK 상태 반환")
    public void testUnFollow() {
        // given
        int toUserId = 123;
        CustomUserDetails customUserDetails = new CustomUserDetails(new User());

        // when
        ResponseEntity<?> response = followApiController.unFollow(customUserDetails, toUserId);

        // then
        verify(followService, times(1)).unFollow(customUserDetails.getUser().getId(), toUserId);
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertAll("response",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(1, responseBody.getCode()),
                () -> assertEquals("언팔로우 성공", responseBody.getMessage()),
                () -> assertNull(responseBody.getData()));
    }
}
