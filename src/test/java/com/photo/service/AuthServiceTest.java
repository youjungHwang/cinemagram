package com.photo.service;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("회원가입 메서드 테스트")
public class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        authService = new AuthService(userRepository, bCryptPasswordEncoder);
    }

    @Test
    @DisplayName("회원가입 성공")
    public void testSignupSuccess() {
        // given
        User user = User.builder()
                .username("testuser")
                .password("password")
                .email("testuser@example.com")
                .build();

        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);

        // when
        User savedUser = authService.signup(user);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getRole()).isEqualTo("ROLE_USER");
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    @DisplayName("회원가입 실패: 필수 필드 누락")
    public void testSignupFailureMissingFields() {
        // given
        User user = User.builder()
                .username("testuser")
                .password("password")
                .build();

        // expect
        exceptionRule.expect(DataIntegrityViolationException.class);

        // when
        authService.signup(user);
    }

    @Test
    @DisplayName("회원가입 실패: 중복된 유저명")
    public void testSignupFailureDuplicateUsername() {
        // given
        User user = User.builder()
                .username("testuser")
                .password("password")
                .email("testuser@example.com")
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // when
        try {
            authService.signup(user);
            fail("Expected DuplicateKeyException to be thrown");
        } catch (DuplicateKeyException e) {
            // then
            verify(bCryptPasswordEncoder, never()).encode(user.getPassword());
            verify(userRepository, never()).save(any(User.class)); // modify this line
        }
    }




}

