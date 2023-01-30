package com.photo.config.oauth;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import com.photo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomOAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> socialUserInfo = oAuth2User.getAttributes();

        String username = "google_"+(String) socialUserInfo.get("sub");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = (String) socialUserInfo.get("email");
        String name = (String) socialUserInfo.get("name");

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .build();
            return new CustomUserDetails(userRepository.save(user));
        }else {
            return new CustomUserDetails(userEntity);
        }
    }
}
