package com.photo.service;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.image.Image;
import com.photo.domain.image.ImageRepository;
import com.photo.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")
    private String imageUploadRoute;

    @Transactional
    public void imageUpload(CustomUserDetails customUserDetails, ImageUploadDto imageUploadDto) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();

        Path imageFilePath = Paths.get(imageUploadRoute+imageFileName);

        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, customUserDetails.getUser());
        imageRepository.save(image);

    }
}
