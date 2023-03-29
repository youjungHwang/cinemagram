package com.photo.cache

import com.fasterxml.jackson.databind.ObjectMapper
import com.photo.domain.user.User
import com.photo.web.dto.auth.SignupRequestDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.mockito.Mockito.*

class UserRedisTemplateServiceTest { // 전체 통과

    @Mock
    RedisTemplate<String, User> redisTemplate

    @Mock
    ObjectMapper objectMapper

    @Mock
    HashOperations<String, Long, String> hashOperations

    UserRedisTemplateService userRedisTemplateService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this)
        userRedisTemplateService = new UserRedisTemplateService(redisTemplate, objectMapper)
        userRedisTemplateService.hashOperations = hashOperations
    }

    @Test
    void save_validSignupRequestDto_shouldPutToRedis() {
        // Given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .id(1L)
                .username("johnsmith")
                .password("mypassword")
                .email("john@example.com")
                .name("John Smith")
                .build();

        // When
        userRedisTemplateService.save(signupRequestDto)

        // Then
        verify(hashOperations).put(
                UserRedisTemplateService.CACHE_KEY,
                signupRequestDto.id,
                objectMapper.writeValueAsString(signupRequestDto)
        )
    }

    @Test
    void "test find all users"() {
        // Arrange
        def  expected = [
                 SignupRequestDto.builder()
                        .id(1L)
                        .username("user1")
                         .password("password1")
                         .email("user1@example.com")
                         .name("User One")
                         .build(),
                SignupRequestDto.builder()
                        .id(2L)
                        .username("user2")
                        .password("password2")
                        .email("user2@example.com")
                        .name("User Two")
                        .build()
        ]
        def redisHashEntries = [
                "1": '{"id":1,"username":"user1","password":"password1","email":"user1@example.com","name":"User One"}',
                "2": '{"id":2,"username":"user2","password":"password2","email":"user2@example.com","name":"User Two"}'
        ]
        when(hashOperations.entries(UserRedisTemplateService.CACHE_KEY))
                .thenReturn(redisHashEntries)

        redisHashEntries.each { key, value ->
            when(objectMapper.readValue(value, SignupRequestDto.class))
                    .thenReturn(expected.find { it.id == key.toLong() })
        }

        // Act
        def result = userRedisTemplateService.findAll()

        // Assert
        assertEquals(expected, result)
    }



    @Test
    void delete_validId_shouldDeleteFromRedis() {
        // Given
        def id = 1L

        // When
        userRedisTemplateService.delete(id)

        // Then
        verify(hashOperations).delete(UserRedisTemplateService.CACHE_KEY, String.valueOf(id))
    }

}
