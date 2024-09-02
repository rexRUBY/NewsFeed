package com.sparta.newsfeed.follower.dto.requestDto;

import com.sparta.newsfeed.follower.entity.User;
import lombok.Getter;

@Getter
public class FollowerSaveRequestDto {

    private Long followerId;
    private Long userId;
}
