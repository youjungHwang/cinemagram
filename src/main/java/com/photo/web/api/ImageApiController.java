package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.image.Image;
import com.photo.service.ImageService;
import com.photo.web.dto.ResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("/api/feed")
    public ResponseEntity<?> Feed(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @PageableDefault(size=4, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Image> images = imageService.Feed(customUserDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new ResDto<>(1,"피드 성공", images), HttpStatus.OK);
    }


}
