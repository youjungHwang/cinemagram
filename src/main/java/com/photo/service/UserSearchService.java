package com.photo.service;

import com.photo.cache.UserRedisTemplateService;
import com.photo.domain.user.User;
import com.photo.web.dto.auth.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserSearchService {
    private final UserService userService;
    private final UserRedisTemplateService userRedisTemplateService;

    /*
     * 전체 유저 조회하는 메서드
     *  해당 메서드 호출시 DB가 아닌 우선 redis 에서 조회
     *  문제 발생시엔 DB 에서 조회 (failover 고려)
     * */
    public List<SignupRequestDto> searchUserDtoList() {

        // [[ redis ]]
        List<SignupRequestDto> userDtoList = userRedisTemplateService.findAll();
        if(!CollectionUtils.isEmpty(userDtoList)) return userDtoList; // validation 체크
        log.info("[UserSearchService searchUserDtoList userDtoList empty] {} ", userDtoList);

        // 빈 리스트 리턴시 [[ db ]]에서 체크
        return userService.findAll()
                .stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    private SignupRequestDto convertToUserDto(User user) {
        return SignupRequestDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}