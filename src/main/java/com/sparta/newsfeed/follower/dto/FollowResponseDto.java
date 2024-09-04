package com.sparta.newsfeed.follower.dto;

import com.sparta.newsfeed.auth.entity.User;
import com.sparta.newsfeed.follower.entity.Follower;
import lombok.Getter;

@Getter
public class FollowResponseDto {
    private String following;
    private String followed;
    private String status;

    // 친구 신청 서비스에서 사용하는 생성자
    public FollowResponseDto(String followingEmail, String followedEmail, Follower.Status status){
        this.following = followingEmail;
        this.followed = followedEmail;
        this.status = status.toString();
    }

    // 친구 신청 수락 서비스에서 사용하는 생성자
    public FollowResponseDto(User user, Follower.Status status) {
        this.following = user.getEmail();
        this.status = status.toString();
    }
}
