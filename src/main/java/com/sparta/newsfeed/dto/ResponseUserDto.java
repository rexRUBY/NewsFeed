package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResponseUserDto {
    private String email;
    private String name;
    private String phone_number;
    private String nickname;
    private Gender gender;
    private String bio;
    private Date birthday;
}
