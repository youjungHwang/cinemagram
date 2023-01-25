package com.photo.service;

import com.photo.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void ImageLikes(int imageId, int sessionId) {
        likesRepository.cLikes(imageId, sessionId);
    }
    @Transactional
    public void ImageUnLikes(int imageId, int sessionId) {
        likesRepository.cUnLikes(imageId, sessionId);
    }
}
