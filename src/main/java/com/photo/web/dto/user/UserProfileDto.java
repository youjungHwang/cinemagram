package com.photo.web.dto.user;

import com.photo.domain.user.User;
import lombok.Data;


@Data
public class UserProfileDto {
    private boolean pageUserState;
    private int imageCount;
    private User user;

    private boolean followState;
    private int followCount;
}
