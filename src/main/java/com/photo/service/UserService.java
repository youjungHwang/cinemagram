package com.photo.service;

import com.photo.domain.follow.FollowRepository;
import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import com.photo.handler.exception.CustomApiException;
import com.photo.handler.exception.CustomException;
import com.photo.handler.exception.CustomValidationApiException;
import com.photo.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;



@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String imageUploadRoute;

    @Transactional
    public User profileImageUpdate(int sessionId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename();

        Path imageFilePath = Paths.get(imageUploadRoute+imageFileName);

        try{
            Files.write(imageFilePath, profileImageFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(sessionId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다."));
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;

    }

    @Transactional(readOnly = true)
    public UserProfileDto profile(int pageUserId, int sessionId) {
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(
                () -> new CustomException("해당 profile 페이지는 없는 페이지입니다."));

        dto.setUser(userEntity);
        dto.setPageUserState(pageUserId == sessionId);
        dto.setImageCount(userEntity.getImages().size());

       int followState = followRepository.cFollowState(sessionId, pageUserId);
       int followCount = followRepository.cFollowCount(pageUserId);

       dto.setFollowState(followState == 1);
       dto.setFollowCount(followCount);

        userEntity.getImages().forEach((image) -> {
            image.setLikesCount(image.getLikes().size());
        });

        return dto;
    }


    @Transactional
    public User userUpdate(int id, User userUpdateDto){ // userUpdateDto.toEntity()
        User userEntity = userRepository.findById(id).orElseThrow(()-> new CustomValidationApiException("해당 유저를 찾을 수 없습니다."));

        String rawPassword = userUpdateDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setName(userUpdateDto.getName());
        userEntity.setPassword(encPassword);
        userEntity.setWebsite(userUpdateDto.getWebsite());
        userEntity.setBio(userUpdateDto.getBio());
        userEntity.setPhone(userUpdateDto.getPhone());
        userEntity.setGender(userUpdateDto.getGender());
        return userEntity;
    }

}
