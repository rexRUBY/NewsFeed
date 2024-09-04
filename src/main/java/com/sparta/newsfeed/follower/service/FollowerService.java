package com.sparta.newsfeed.follower.service;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.auth.entity.User;
import com.sparta.newsfeed.auth.repository.UserRepository;
import com.sparta.newsfeed.follower.dto.FollowRequestDto;
import com.sparta.newsfeed.follower.dto.FollowResponseDto;
import com.sparta.newsfeed.follower.entity.Follower;
import com.sparta.newsfeed.follower.repository.FollowerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FollowerService {

    FollowerRepository followerRepository;
    UserRepository userRepository;

    public FollowResponseDto saveFollower(AuthUser authUser, FollowRequestDto requestDto) {
        // 친구신청 수락인 찾아오기
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new IllegalArgumentException("신청 사용자를 찾을 수 없습니다."));
        User follower = userRepository.findByEmail(requestDto.getEmail());
        if (follower == null) {
            throw new IllegalArgumentException("친구 사용자를 찾을 수 없습니다.");
        }

        // 친구 신청 관계 맺기
        Follower follow = new Follower(user, follower);
        followerRepository.save(follow);
        return new FollowResponseDto(user.getEmail(), follower.getEmail(), follow.getStatus());
    }


    public FollowResponseDto acceptFollow(AuthUser authUser, FollowRequestDto requestDto) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("신청 사용자를 찾을 수 없습니다."));

        User follower = userRepository.findByEmail(requestDto.getEmail());
        System.out.println(follower.getUsername());

        Follower followRequest = followerRepository.findByFollowerIdAndUserId(follower.getId(), user.getId())
                .orElseThrow(() -> new IllegalArgumentException("친구 요청을 찾을 수 없습니다."));

        followRequest.setStatus(Follower.Status.accepted);

        return new FollowResponseDto(follower, followRequest.getStatus());
    }




    // user의 전체 친구 목록 조회
    public List<User> getAllFollowers(User user) {
        // 친구 신청을 보낸 유저 (userId) 목록 가져오기
        List<Follower> followedByUser = followerRepository.findAllByUserIdAndStatus(user.getId(), Follower.Status.accepted);

        // 친구 신청을 받은 유저 (followerId) 목록 가져오기
        List<Follower> followersOfUser = followerRepository.findAllByFollowerIdAndStatus(user.getId(), Follower.Status.accepted);

        // 친구 목록을 저장할 리스트
        List<User> friends = new ArrayList<>();

        // user가 다른 유저를 팔로우한 경우 친구 목록 추가
        for (Follower follow : followedByUser) {
            User followedUser = userRepository.findById(follow.getFollowerId())
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            friends.add(followedUser);
        }

        // 다른 유저가 user를 팔로우한 경우 친구 목록 추가
        for (Follower follow : followersOfUser) {
            User followerUser = userRepository.findById(follow.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            friends.add(followerUser);
        }
        return friends;
    }
}
