package com.photo.config.oauth;

import com.photo.config.auth.CustomUserDetails;
import com.photo.config.oauth.provider.GoogleUserInfo;
import com.photo.config.oauth.provider.NaverUserInfo;
import com.photo.config.oauth.provider.OAuth2UserInfo;
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
        return commonOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User commonOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else {
            System.out.println("Cinemagram은 구글과 네이버 계정 로그인만 지원합니다.");
        }

        User userEntity =
                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        if(userEntity != null) {
            return new CustomUserDetails(userEntity);
        }else {
            User user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .password(password)
                    .role("ROLE_USER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            return new CustomUserDetails(userRepository.save(user));
        }
    }
}
