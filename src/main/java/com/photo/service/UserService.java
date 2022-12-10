package com.photo.service;

import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import com.photo.handler.exception.CustomValidationApiException;
import com.photo.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
