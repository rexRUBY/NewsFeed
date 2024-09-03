package com.sparta.newsfeed.auth.controller;

import com.sparta.newsfeed.auth.dto.UserRequestDto;
import com.sparta.newsfeed.auth.dto.UserResponseDto;
import com.sparta.newsfeed.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public UserResponseDto signup(@RequestBody UserRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/users/login")
    public UserResponseDto login(@RequestBody UserRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
}
