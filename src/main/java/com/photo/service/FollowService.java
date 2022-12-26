package com.photo.service;

import com.photo.domain.follow.FollowRepository;
import com.photo.handler.exception.CustomApiException;
import com.photo.web.dto.follow.FollowInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List<FollowInfoDto> followInfoList(int sessionId, int pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if((SELECT 1 FROM follow WHERE fromUserId=? AND toUserId= u.id), 1, 0) followState, ");
        sb.append("if((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN follow f ");
        sb.append("ON u.id = f.toUserId ");
        sb.append("WHERE f.fromUserId=?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, sessionId)
                .setParameter(2, sessionId)
                .setParameter(3, pageUserId);

        List<Object[]> results = query.getResultList();
        List<FollowInfoDto> followInfoDtos = results.stream()
                .map(o -> new FollowInfoDto(o))
                .collect(Collectors.toList());
        return followInfoDtos;
    }

    @Transactional
    public void follow(int fromUserId, int toUserId) {
        try{
            followRepository.cFollow(fromUserId,toUserId);
        }catch (Exception e) {
            throw new CustomApiException("이미 팔로우 하고 있는 유저입니다.");
        }
    }

    @Transactional
    public void unFollow(int fromUserId, int toUserId) {
        followRepository.cUnFollow(fromUserId,toUserId);
    }
}
