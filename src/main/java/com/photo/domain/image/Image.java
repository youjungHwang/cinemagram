package com.photo.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photo.domain.BaseTimeEntity;
import com.photo.domain.user.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption;
    private String imageUrl;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @Builder
    public Image(String caption, String imageUrl, User user) {
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.user = user;
    }



}
