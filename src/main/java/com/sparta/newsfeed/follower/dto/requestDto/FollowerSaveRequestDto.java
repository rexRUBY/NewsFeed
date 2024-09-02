package com.sparta.newsfeed.follower.dto.requestDto;

import ch.qos.logback.core.status.Status;
import lombok.Getter;

@Getter
public class FollowerSaveRequestDto {

    private User follower;
    private User user;
}
