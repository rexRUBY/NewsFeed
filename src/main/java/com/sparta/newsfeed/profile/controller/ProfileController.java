package com.sparta.newsfeed.profile.controller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.profile.dto.RequestUserDto;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.profileservice.ProfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/getinfo")
    public ResponseUserDto getprofile(@Auth AuthUser authuser){
        return profileService.getprofile(authuser);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseUserDto> updateprofile(@Auth AuthUser authuser, @RequestBody RequestUserDto requestDto){
        return ResponseEntity.ok(profileService.updateprofile(authuser,requestDto));
    }

}
