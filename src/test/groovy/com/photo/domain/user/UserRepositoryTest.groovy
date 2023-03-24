//package com.photo.domain.user
//
//import com.photo.AbstractIntegrationContainerBaseTest
//import org.springframework.beans.factory.annotation.Autowired
//
//
//class UserRepositoryTest extends AbstractIntegrationContainerBaseTest { // 통합테스트 환경 구성 됨 (추상 클래스를 상속 받아서)
//
//    @Autowired // 빈을 주입 받음
//    private UserRepository userRepository // userRepository을 통해서 CRUD 테스트 해 볼 것
//
//    /*
//    * 여기서 생각해볼 것
//    *   여기서 테스트 할 DB를 어떤 DB를 사용해야 할까?
//    *   testcontainers 사용해서 통합테스트 환경 구축
//    */
//
//    void setup() {
//        userRepository.deleteAll()
//    }
//
//    /*
//      빌더패턴으로 클래스 만들어서 저장
//    */
//    def "UserRepository save"() {
//
//        given:
//        String username = "test-apple"
//        String password = "562456"
//        String email = "apple@test.com"
//        String name = "테스트 사과"
//
//        def user = User.builder()
//                .username(username)
//                .password(password)
//                .email(email)
//                .name(name)
//                .build()
//        when:
//        def userEntity = userRepository.save(user)
//
//        then:
//        userEntity.getUsername() == username
//        userEntity.getPassword() == password
//        userEntity.getEmail() == email
//        userEntity.getName() == name
//    }
//
//    def "UserRepository findByUsername"() {
//
//        given:
//        String username = "테스트 바나나"
//
//        def user = User.builder()
//                .username(username)
//                .build()
//        when:
//        def entity = userRepository.save(user)
//        def result = userRepository.findByUsername(entity.getId())
//
//
//        then:
//        entity.getId() == result.getId()
//        entity.getUsername() == result.getUsername()
//    }
//}
