package com.sparta.newsfeed.auth.dto;

import com.sparta.newsfeed.auth.entity.Gender;
import com.sparta.newsfeed.auth.entity.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String nickName;
    private String imageUrl;
    private Gender gender;
    private String bio;
    private Date birthDay;

    //회원가입에서 사용하는 생성자
    public UserResponseDto(User newUser) {
        this.id = newUser.getId();
        this.username = newUser.getUsername();
        this.email = newUser.getEmail();
        this.phoneNumber = newUser.getPhoneNumber();
    }
}
