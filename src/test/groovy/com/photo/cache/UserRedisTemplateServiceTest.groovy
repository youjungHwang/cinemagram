package com.photo.cache

import com.photo.AbstractIntegrationContainerBaseTest
import com.photo.web.dto.auth.SignupReqDto
import org.springframework.beans.factory.annotation.Autowired

class UserRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private UserRedisTemplateService userRedisTemplateService

    def setup() {
        userRedisTemplateService.findAll()
                .forEach(dto -> {
                    userRedisTemplateService.delete(dto.getId())
                })
    }

    def "save success"() {
        given:
        String username = "test-apple"
        String password = "1234"
        String email = "apple@test.com"
        String name = "테스트용-사과"

        SignupReqDto dto =
                SignupReqDto.builder()
                        .id(1)
                        .username(username)
                        .password(password)
                        .email(email)
                        .name(name)
                        .build()

        when:
        userRedisTemplateService.save(dto)
        List<SignupReqDto> result = userRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1
        result.get(0).username == username
        result.get(0).password == password
        result.get(0).email == email
        result.get(0).name == name

    }

//    def "success fail"() {
//        given:
//        SignupReqDto dto =
//                UserDto.builder()
//                        .build()
//
//        when:
//        userRedisTemplateService.save(dto)
//        List<SignupReqDto> result = userRedisTemplateService.findAll()
//
//        then:
//        result.size() == 0
//    }
//
//    def "delete"() {
//        given:
//        String userName = "userNameD"
//        String userAddress = "userAddressD"
//        SignupReqDto dto =
//                UserDto.builder()
//                        .id(1L)
//                        .userName(userName)
//                        .userAddress(userAddress)
//                        .build()
//
//        when:
//        userRedisTemplateService.save(dto)
//        userRedisTemplateService.delete(dto.getId())
//        def result = userRedisTemplateService.findAll()
//
//        then:
//        result.size() == 0
//    }
//
}
