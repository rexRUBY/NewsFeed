package com.sparta.newsfeed.follower.service;

import com.sparta.newsfeed.follower.dto.requestDto.FollowerSaveRequestDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowerSaveResponseDto;
import com.sparta.newsfeed.follower.entity.Follower;
import com.sparta.newsfeed.follower.repository.FollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    // post - 친구 팔로우
    @Transactional
    public FollowerSaveResponseDto saveFollower(FollowerSaveRequestDto followerSaveRequestDto) {
        // 사용자의 id가 존재하는지 체크
        User user = userRepository.findById(followerSaveRequestDto.getUser())
                .orElseThrow(() -> new NullPointerException("해당 유저가 존재하지 않습니다."));

        // 추가하려는 타 유저의 id가 존재하는지 체크 -> 없다면 추가
        User follower = userRepository.findById(followerSaveRequestDto.getFollower())
                .orElseThrow(() -> new NullPointerException("해당 유저가 존재하지 않습니다."));

        // 이미 팔로우한 유저를 팔로우하는지 체크
        if (followerRepository.existsByUserAndFollower(user, follower)) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        }

        Follower newFollower = new Follower(
                user,
                follower
        );

        Follower saveFollower = followerRepository.save(newFollower);
        newFollower.setFollowStatus();
        return new FollowerSaveResponseDto(saveFollower);
    }

    // delete - 친구 팔로우 취소
    @Transactional
    public void deleteFollower(User userId) {
        // 없는 사용자를 팔로우 취소하려고 하는지 체크
        User userCheck = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("해당 사용자가 존재하지 않습니다."));

        Follower follower = new Follower();
        followerRepository.delete(userId);
        follower.setFollowStatus();
    }
}
