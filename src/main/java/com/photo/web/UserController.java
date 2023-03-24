package com.photo.web;

import com.photo.config.auth.CustomUserDetails;
import com.photo.service.UserService;
import com.photo.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserProfileDto userProfileDto = userService.profile(pageUserId, customUserDetails.getUser().getId());
        model.addAttribute("dto", userProfileDto);
        model.addAttribute("sessionId", customUserDetails.getUser().getId());
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("sessionId", customUserDetails.getUser().getId());
        model.addAttribute("sessionUser", customUserDetails.getUser());
        model.addAttribute("socialSessionUser", customUserDetails.getAttributes());
        return "user/update";
    }

}
