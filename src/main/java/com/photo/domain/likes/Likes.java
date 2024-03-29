package com.photo.domain.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photo.domain.BaseTimeEntity;
import com.photo.domain.image.Image;
import com.photo.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@RedisHash("likes")
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "imageId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}
