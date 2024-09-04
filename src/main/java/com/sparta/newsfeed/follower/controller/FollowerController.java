package com.sparta.newsfeed.follower.controller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
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
    public ResponseEntity<FollowerSaveResponseDto> saveFollower(@Auth AuthUser authUser, @RequestBody FollowerSaveRequestDto followerSaveRequestDto) {
        return ResponseEntity.ok(followerService.saveFollower(authUser, followerSaveRequestDto));
    }

//    // post - 친구 신청 수락
//    @PostMapping("")
//    public ResponseEntity<FollowAcceptResponseDto> acceptFollow(@RequestParam String email) {
//        return ResponseEntity.ok(followerService.acceptFollow(email));
//    }

//    // delete - 친구 삭제
//    @DeleteMapping("/users/{userId}/followers/{followerId}")
//    public void deleteFollower(@PathVariable Long followerId, @PathVariable Long userId) {
//        followerService.deleteFollower(followerId, userId);
//    }
}