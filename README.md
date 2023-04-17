# Cinemagram
- 인스타그램 영화 버전 프로젝트
- 애플리케이션 UI는 기존 코드를 사용, Thymeleaf 추가
- 기능 구현 및 재사용성을 고려한 Validation AOP, ExceptionHandler 예외처리
- 프로젝트 구현, 추가로 학습한 내용, 트러블 슈팅 경험은 기술 블로그에 기록

## 개발환경
- IntelliJ
- Java 11
- Spring Boot 2.6
- MySQL
- Gradle
- Spring Security
- OAuth2.0
- HTML, CSS, Javascript  


## ERD
<img width="674" alt="erd" src="https://user-images.githubusercontent.com/78125105/219604377-3fd6c117-5ccd-4cc5-bebd-44e2622b9fe3.png">  

## 애플리케이션 작동
프로필 변경, 이미지 추가

![프로필변경_이미지추가](https://user-images.githubusercontent.com/78125105/219615464-dd3a89d1-a4b7-4171-b610-0a582720b3d7.gif)

댓글, 좋아요

![댓글_좋아요](https://user-images.githubusercontent.com/78125105/219616194-a0006734-bcb6-4e9a-8a3d-c984c91c463d.gif)


## 주요 기능, 추가한 기능
1. 일반, 소셜 회원가입
2. 로그인, 로그아웃
3. 프로필 사진 변경
4. 이미지 추가
5. 회원정보 변경
6. 팔로우, 언팔로우
7. 좋아요, 좋아요 취소
8. 댓글, 댓글 삭제
9. 도커 추가
10. 대용량 트래픽을 고려하여 redis 추가


## REST API URL 설계
/api/v1 경로는 API의 버전을 나타내며, 이를 통해 클라이언트는 서로 다른 버전의 API를 호출할 수 있습니다.

#### Comment 댓글
댓글 생성 API: POST /api/v1/comments  

댓글 삭제 API: DELETE /api/v1/comments/{id}

#### Follow 팔로우
구독 추가 API : POST /api/v1/follow/{to-user-id}

구독 취소 API : DELETE /api/v1/follow/{to-user-id}

#### Image 사진
피드 조회 API : GET /api/v1/feed

좋아요 API : POST /api/v1/image/{image-id}/likes

좋아요 취소 API : DELETE /api/v1/image/{image-id}/likes

#### User 회원
회원 프로필 사진 변경 API : PUT /api/v1/user/{session-id}/profile-image-url

구독한 유저 정보 조회 API : GET /api/v1/user/{page-user-id}/follow

회원 정보 수정 API : PUT /api/v1/user/{id}

DB 유저 정보 redis 동기화 API : GET /api/v1/redis/save








