package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.service.FollowService;
import com.photo.web.dto.ResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("follow/{to-user-id}")
    public ResponseEntity<?> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId) {
        followService.follow(customUserDetails.getUser().getId(),toUserId);
        return new ResponseEntity<>(new ResDto<>(1,"팔로우 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("follow/{to-user-id}")
    public ResponseEntity<?> unFollow(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toUserId) {
        followService.unFollow(customUserDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new ResDto<>(1,"언팔로우 성공", null), HttpStatus.OK);
    }
}
