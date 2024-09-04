package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.Gender;
import com.sparta.newsfeed.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDto {
    private Long id;
    private String email;
    private String name;
    private String phone_number;
    private String nickname;
    private Gender gender;
    private String bio;
    private Date birthday;

    public ResponseUserDto(User newUser) {
        this.id = newUser.getId();
        this.name = newUser.getName();
        this.email = newUser.getEmail();
    }
}
