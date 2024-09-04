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
    private String phone_number;
    private String nickname;
    private Gender gender;
    private String bio;
    private String birthday;
}
