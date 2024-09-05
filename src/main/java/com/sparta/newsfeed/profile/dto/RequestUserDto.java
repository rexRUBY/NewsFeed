package com.sparta.newsfeed.profile.dto;

import com.sparta.newsfeed.profile.entity.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;
    private String bio;
    private String birthday;
    private String inputPassword;
    private String editPassword;
}
