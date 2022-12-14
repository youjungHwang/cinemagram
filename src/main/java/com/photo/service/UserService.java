package com.photo.service;

import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import com.photo.handler.exception.CustomException;
import com.photo.handler.exception.CustomValidationApiException;
import com.photo.web.dto.user.UserProfileDto;
import com.photo.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional(readOnly = true)
    public UserProfileDto profile(int pageUserId, int sessionId) {
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(
                () -> new CustomException("해당 profile 페이지는 없는 페이지입니다."));

        dto.setUser(userEntity);
        dto.setPageUserState(pageUserId == sessionId);
        dto.setImageCount(userEntity.getImages().size());


        return dto;
    }


    @Transactional
    public User userUpdate(int id, User userUpdateDto){
        User userEntity = userRepository.findById(id).orElseThrow(()-> new CustomValidationApiException("해당 유저를 찾을 수 없습니다."));

        userEntity.setName(userUpdateDto.getName());

        String rawPassword = userUpdateDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setWebsite(userUpdateDto.getWebsite());
        userEntity.setBio(userUpdateDto.getBio());
        userEntity.setPhone(userUpdateDto.getPhone());
        userEntity.setGender(userUpdateDto.getGender());
        return userEntity;
    }

}
