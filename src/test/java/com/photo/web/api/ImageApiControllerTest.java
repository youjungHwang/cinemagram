package com.photo.web.api;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.image.Image;
import com.photo.domain.user.User;
import com.photo.service.ImageService;
import com.photo.service.LikesService;
import com.photo.web.dto.ResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageApiControllerTest {
    private ImageService imageService;
    private LikesService likesService;
    private ImageApiController imageApiController;

    @BeforeEach
    public void setUp() {
        imageService = mock(ImageService.class);
        likesService = mock(LikesService.class);
        imageApiController = new ImageApiController(imageService, likesService);
    }

    @Test
    @DisplayName("피드 조회 - 이미지 목록을 성공적으로 반환하고 상태 코드 OK를 반환해야 함")
    public void testFeed() {
        // given
        CustomUserDetails customUserDetails = new CustomUserDetails(new User());

        List<Image> images = new ArrayList<>();
        images.add(new Image());
        Page<Image> pageImages = new PageImpl<>(images);

        // when
        when(imageService.Feed(customUserDetails.getUser().getId(), Pageable.unpaged())).thenReturn(pageImages);
        ResponseEntity<?> response = imageApiController.Feed(customUserDetails, Pageable.unpaged());

        // then
        verify(imageService, times(1)).Feed(customUserDetails.getUser().getId(), Pageable.unpaged());
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertAll("response",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(1, responseBody.getCode()),
                () -> assertEquals("피드 성공", responseBody.getMessage()),
                () -> assertEquals(pageImages, responseBody.getData()));
    }

    @Test
    @DisplayName("이미지 좋아요 - 성공적으로 좋아요를 누르고 상태 코드 CREATED를 반환해야 함")
    public void testImageLikes() {
        // given
        int imageId = 123;
        CustomUserDetails customUserDetails = new CustomUserDetails(new User());


        // when
        ResponseEntity<?> response = imageApiController.ImageLikes(imageId, customUserDetails);

        // then
        verify(likesService, times(1)).ImageLikes(imageId, customUserDetails.getUser().getId());
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertAll("response",
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(1, responseBody.getCode()),
                () -> assertEquals("좋아요 성공", responseBody.getMessage()),
                () -> assertNull(responseBody.getData()));
    }

    @Test
    @DisplayName("이미지 좋아요 취소 - 성공적으로 좋아요를 취소하고 상태 코드 OK를 반환해야 함")
    public void testImageUnLikes() {
        // given
        int imageId = 123;
        CustomUserDetails customUserDetails = new CustomUserDetails(new User());


        // when
        ResponseEntity<?> response = imageApiController.ImageUnLikes(imageId, customUserDetails);

        // then
        verify(likesService, times(1)).ImageUnLikes(imageId, customUserDetails.getUser().getId());
        ResDto<?> responseBody = (ResDto<?>) response.getBody();
        assertAll("response",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(1, responseBody.getCode()),
                () -> assertEquals("좋아요 취소 성공", responseBody.getMessage()),
                () -> assertNull(responseBody.getData()));
    }

}
