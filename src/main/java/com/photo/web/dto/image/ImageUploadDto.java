package com.photo.web.dto.image;

import com.photo.domain.image.Image;
import com.photo.domain.user.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {
    private MultipartFile file;
    private String caption;

    @Builder
    public ImageUploadDto(MultipartFile file, String caption) {
        this.file = file;
        this.caption = caption;
    }

    public Image toEntity(String imageUrl, User user){
        return Image.builder()
                .caption(caption)
                .imageUrl(imageUrl)
                .user(user)
                .build();

    }
}
