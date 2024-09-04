package com.sparta.newsfeed.follower.dto.responseDto;

import com.sparta.newsfeed.auth.entity.User;
import com.sparta.newsfeed.follower.entity.Follower;
import lombok.Getter;

@Getter
public class FollowerSaveResponseDto {
    private final String followerEmail;
    private final String userEmail;

    public FollowerSaveResponseDto(Follower saveFollower) {
        this.followerEmail = saveFollower.getFollower().getEmail();
        this.userEmail = saveFollower.getUser().getEmail();
    }
}
