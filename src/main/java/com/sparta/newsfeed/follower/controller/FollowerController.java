package com.sparta.newsfeed.follower.controller;

import com.sparta.newsfeed.follower.dto.requestDto.FollowerSaveRequestDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowerSaveResponseDto;
import com.sparta.newsfeed.follower.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    // post - 친구 추가
    @PostMapping("/users/followers")
    public ResponseEntity<FollowerSaveResponseDto> addFollower(@RequestBody FollowerSaveRequestDto followerSaveRequestDto) {
        return ResponseEntity.ok(followerService.saveFollower(followerSaveRequestDto));
    }

    // delete - 친구 삭제
    @DeleteMapping("/users/followers/{followerId}")
    public void deleteFollower(@PathVariable User follower) {
        followerService.deleteFollower(follower);
    }
}