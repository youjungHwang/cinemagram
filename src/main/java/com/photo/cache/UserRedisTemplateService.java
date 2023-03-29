package com.photo.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photo.domain.user.User;
import com.photo.web.dto.auth.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRedisTemplateService {

    private static final String CACHE_KEY = "USER"; // 상수, 키값은 USER를 사용

    private final RedisTemplate<String, User> redisTemplate;
    private final ObjectMapper objectMapper; // objectMapper 빈으로 등록해서 싱글톤으로 사용

    private HashOperations<String, Long , String> hashOperations; // <키,서브키(PK), 밸류>

    /*
     * 생성자 호출 후에 redisTemplate 에서 제공하는 해시자료구조 이용
     * */
    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(SignupRequestDto signupRequestDto) {
        if(Objects.isNull(signupRequestDto) || Objects.isNull(signupRequestDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    signupRequestDto.getId(),
                    objectMapper.writeValueAsString(signupRequestDto));
            log.info("[UserRedisTemplateService save success] id: {}", signupRequestDto.getId());
        } catch (Exception e) {
            log.error("[UserRedisTemplateService save error] {}", e.getMessage());
        }
    }

    public List<SignupRequestDto> findAll() {
        try {
            List<SignupRequestDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                SignupRequestDto signupRequestDto = objectMapper.readValue(value, SignupRequestDto.class);
                list.add(signupRequestDto);
                log.info("using redis - findAll method result {} ", list );
            }
            return list;

        } catch (Exception e) {
            log.error("[UserRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList(); // 문제가 있으면 빈 리스트를 리턴
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[UserRedisTemplateService delete]: {} ", id);
    }


}