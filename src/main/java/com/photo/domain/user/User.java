package com.photo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor // 전체 생성자(빈 생성자 제외)
@NoArgsConstructor  // 빈 생성자
@Data
@Entity             // DB에 테이블을 생성해줌, @Entity가 붙은 클래스는 JPA가 관리합니다. 따라서 JPA를 사용해서 테이블과 매핑할 클래스는 필수로 붙여야합니다.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO가 default, IDENTITY 번호 증가 전략이 DB를 따라간다.
    private int id;

    private String username;
    private String password;
    private String name;
    private String website;
    private String bio;
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; // 유저 사진
    private String role;            // 권한


    // 생성 시간은 자동으로 들어감
    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }






}
