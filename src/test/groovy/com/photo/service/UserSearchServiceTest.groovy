package com.photo.service

import com.photo.cache.UserRedisTemplateService
import com.photo.domain.user.User
import com.google.common.collect.Lists
import spock.lang.Specification

class UserSearchServiceTest extends Specification { // 전체 통과
    private UserSearchService userSearchService

    // mocking
    private  UserService userService = Mock()
    private  UserRedisTemplateService userRedisTemplateService = Mock()

    private List<User> userList

    def setup(){
        userSearchService = new UserSearchService(userService, userRedisTemplateService) // mocking 한 객체 추가

        userList = Lists.newArrayList(
                User.builder()
                        .id(1)
                        .username("김철수")
                        .password("1234")
                        .email("cs@test.com")
                        .name("테스트 유저1")
                        .build(),
                User.builder()
                        .id(2)
                        .username("고영희")
                        .password("2563")
                        .email("oh@test.com")
                        .name("테스트 유저2")
                        .build()
        )
    }

    def "레디스 장애시 DB를 이용 하여 유저 데이터 조회"() {

        when:
        userRedisTemplateService.findAll() >> []
        userService.findAll() >> userList

        def result = userSearchService.searchUserDtoList()

        then:
        result.size() == 2
    }

}