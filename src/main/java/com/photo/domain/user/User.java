package com.photo.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.photo.domain.BaseTimeEntity;
import com.photo.domain.image.Image;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 20)
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


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // 응답시 무한 참조 막기위함, image 엔티티에 있는 user는 무시하고 JSON 파싱한다.
    private List<Image> images;


}
