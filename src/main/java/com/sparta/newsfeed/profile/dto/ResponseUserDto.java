package com.sparta.newsfeed.profile.dto;

import com.sparta.newsfeed.profile.entity.Gender;
import com.sparta.newsfeed.profile.entity.User;
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
        this.phone_number = newUser.getPhone_number();
        this.nickname = newUser.getNickname();
        this.bio = newUser.getBio();
        this.birthday = newUser.getBirthday();
        this.gender = newUser.getGender();
    }
}
