package com.photo.service;

import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service // 1. IoC 등록 2. 트랜잭션 관리
public class AuthService {

    // DB INSERT 위해 Repository DI 해줌
    private final UserRepository userRepository;

    /* 회원가입 */
    public User signup(User user) { // (User user) : 외부 통신을 통해 받은 데이터(user)를 User 오브젝트에 담음
        User userEntity = userRepository.save(user); // 리턴은 넣은(user) 타입으로 함
                                                     // User userEntity :  DB에 있는 데이터를 User 오브젝트에 담음
        return userEntity;
    }
}
