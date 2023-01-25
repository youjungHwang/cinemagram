package com.photo.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photo.domain.BaseTimeEntity;
import com.photo.domain.likes.Likes;
import com.photo.domain.user.User;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption;
    private String imageUrl;

    @JsonIgnoreProperties({"images"}) //
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    @Transient
    @Setter
    private boolean likesState;

    @Transient
    @Setter
    private int likesCount;


    @Builder
    public Image(String caption, String imageUrl, User user) {
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
