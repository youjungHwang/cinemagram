package com.photo.web.dto.follow;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class FollowInfoDto {
    private Long id;

    private String username;
    private String profileImageUrl;

    private Integer followState;
    private Integer equalUserState;

    public FollowInfoDto(Object[] object) {
        this.id = (Long) object[0];
        this.username = (String) object[1];
        this.profileImageUrl = (String) object[2];
        this.followState = Integer.parseInt(String.valueOf(object[3]));
        this.equalUserState = Integer.parseInt(String.valueOf(object[4]));
    }

}
