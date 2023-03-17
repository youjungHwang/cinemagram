# Cinemagram
### Description
***
- 인스타그램 영화 버전 프로젝트
- 애플리케이션 UI는 기존 코드를 사용, Thymeleaf 추가
- 기능 구현 및 재사용성을 고려한 Validation AOP, ExceptionHandler 예외처리
- 프로젝트 구현, 추가로 학습한 내용, 트러블 슈팅 경험은 기술 블로그에 기록

### 개발환경
***
- IntelliJ
- Java 1.8 
- Spring Boot 2.6
- MySQL
- Gradle
- Spring Security
- OAuth2.0
- HTML, CSS, Javascript

### ERD
***
<img width="674" alt="erd" src="https://user-images.githubusercontent.com/78125105/219604377-3fd6c117-5ccd-4cc5-bebd-44e2622b9fe3.png">  

### 애플리케이션 작동
***
프로필 변경, 이미지 추가

![프로필변경_이미지추가](https://user-images.githubusercontent.com/78125105/219615464-dd3a89d1-a4b7-4171-b610-0a582720b3d7.gif)

댓글, 좋아요

![댓글_좋아요](https://user-images.githubusercontent.com/78125105/219616194-a0006734-bcb6-4e9a-8a3d-c984c91c463d.gif)


### 주요 기능
***
1. 일반, 소셜 회원가입
2. 로그인, 로그아웃
3. 프로필 사진 변경
4. 이미지 추가
5. 회원정보 변경
6. 팔로우, 언팔로우
7. 좋아요, 좋아요 취소
8. 댓글, 댓글 삭제

### 요구사항 별 비즈니스 로직
***
### Comment 댓글
/api/comment  
댓글 등록

/api/comment/{id}  
댓글 삭제

### Follow 팔로우
/api/follow/{toUserId}  
toUserId를 팔로우 한다

/api/follow/{toUserId}








