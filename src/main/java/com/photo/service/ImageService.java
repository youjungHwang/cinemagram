package com.photo.service;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.image.Image;
import com.photo.domain.image.ImageRepository;
import com.photo.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> popularImages() {
        return imageRepository.cPopularImages();
    }

    @Transactional(readOnly = true)
    public Page<Image> Feed(int sessionId, Pageable pageable) {
        Page<Image> images = imageRepository.cFeed(sessionId, pageable);

        images.forEach((image -> {
            image.setLikesCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if(like.getUser().getId() == sessionId) {
                    image.setLikesState(true);
                }
            });

        }));

        return images;
    }

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
