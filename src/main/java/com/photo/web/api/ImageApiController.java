package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.image.Image;
import com.photo.service.ImageService;
import com.photo.service.LikesService;
import com.photo.web.dto.ResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/feed")
    public ResponseEntity<?> Feed(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @PageableDefault(size=4, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Image> images = imageService.Feed(customUserDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new ResDto<>(1,"피드 성공", images), HttpStatus.OK);
    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> ImageLikes(@PathVariable Long imageId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        likesService.ImageLikes(imageId, customUserDetails.getUser().getId());
        return new ResponseEntity<>(new ResDto<>(1,"좋아요 성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> ImageUnLikes(@PathVariable Long imageId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        likesService.ImageUnLikes(imageId, customUserDetails.getUser().getId());
        return new ResponseEntity<>(new ResDto<>(1,"좋아요 취소 성공",null), HttpStatus.OK);
    }

}
