package com.photo.web;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.image.Image;
import com.photo.handler.exception.CustomValidationException;
import com.photo.service.ImageService;
import com.photo.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/feed"})
    public String feed(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("sessionId", customUserDetails.getUser().getId());
        return "image/feed";
    }

    @GetMapping("/image/popular")
    public String popular(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("sessionId", customUserDetails.getUser().getId());

        List<Image> images = imageService.popularImages();
        model.addAttribute("images", images);
        return "/image/popular";
    }

    @GetMapping("/image/upload")
    public String upload(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("sessionId", customUserDetails.getUser().getId());
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(@AuthenticationPrincipal CustomUserDetails customUserDetails, ImageUploadDto imageUploadDto) {
        if(imageUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null);
        }
        imageService.imageUpload(customUserDetails, imageUploadDto);
        return "redirect:/user/"+customUserDetails.getUser().getId();
    }

}
