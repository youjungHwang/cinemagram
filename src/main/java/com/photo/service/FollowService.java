package com.photo.service;

import com.photo.domain.follow.FollowRepository;
import com.photo.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;


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
