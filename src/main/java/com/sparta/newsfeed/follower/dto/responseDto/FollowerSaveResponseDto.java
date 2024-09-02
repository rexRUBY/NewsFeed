package com.sparta.newsfeed.follower.dto.responseDto;

import com.sparta.newsfeed.follower.entity.Follower;
import com.sparta.newsfeed.follower.entity.User;
import lombok.Getter;

@Getter
public class FollowerSaveResponseDto {
    private final User follower;
    private final User user;
//    private final Follower.Status status;

    public FollowerSaveResponseDto(Follower saveFollower) {
        this.follower = saveFollower.getFollower();
        this.user = saveFollower.getUser();
//        this.status = saveFollower.getStatus();
    }
}
