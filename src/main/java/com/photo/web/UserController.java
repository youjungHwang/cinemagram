package com.photo.web;

import com.photo.config.auth.CustomUserDetails;
import com.photo.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id) {
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // [세션에 접근하는 두가지 방법]
        // 1. @AuthenticationPrincipal 사용
        System.out.println("세션 정보확인: " + customUserDetails.getUser());

        // 2. 직접 세션 찾기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String username = authentication.getName();

        System.out.println("직접 찾은 세션정보 - user: " + user.getUser());
        System.out.println("직접 찾은 세션정보 - username: " + username);

        return "user/update";
    }
}
