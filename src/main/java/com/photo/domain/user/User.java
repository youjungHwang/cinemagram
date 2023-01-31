package com.photo.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photo.domain.BaseTimeEntity;
import com.photo.domain.image.Image;
import lombok.*;
import javax.persistence.*;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    // OAuth2.0
    private String provider;
    private String providerId;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<Image> images;

}
