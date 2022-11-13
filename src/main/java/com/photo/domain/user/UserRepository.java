package com.photo.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속했으면 따로 어노테이션이 없어도 IoC등록이 자동으로 된다.
public interface UserRepository extends JpaRepository<User, Integer> { // 오브젝트, PK의 타입


}
