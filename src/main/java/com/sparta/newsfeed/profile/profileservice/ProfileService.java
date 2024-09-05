package com.sparta.newsfeed.profile.profileservice;

import com.sparta.newsfeed.auth.config.PasswordEncoder;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.profile.dto.RequestUserDto;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.profile.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ProfileService {

    ProfileRepository profileRepository;
    PasswordEncoder passwordEncoder;

    // 프로필 조회
    @Transactional
    public ResponseUserDto getprofile(AuthUser authUser) {
        Long userId = authUser.getId();
        User search_user = profileRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        ResponseUserDto res = new ResponseUserDto();
        res.setEmail(search_user.getEmail());
        res.setGender(search_user.getGender());
        res.setNickname(search_user.getNickname());
        res.setBio(search_user.getBio());
        res.setBirthday(search_user.getBirthday());

        return res;
    }

    //프로필 수정
    @Transactional
    public ResponseUserDto updateprofile(AuthUser authuser, RequestUserDto requestDto) {
        User user = profileRepository.findById(authuser.getId()).orElseThrow(() -> new IllegalArgumentException("올바른 요청이 아닙니다."));

        String inputPassword = requestDto.getInputPassword();
        String editPassword = requestDto.getEditPassword();

        //패스워드 수정 시 본인확인을 위해 비밀번호를 확인
        if(!inputPassword.isBlank()) {
            if (inputPassword.equals(editPassword)) {
                throw new IllegalArgumentException("현재 비밀번호와 같은 비밀번호로 설정할 수 없습니다.");
            } else if (!passwordEncoder.matches(inputPassword, user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            user.setPassword(passwordEncoder.encode(editPassword));
        }

        if (requestDto.getName() != null)
            user.setName(requestDto.getName());
        if (requestDto.getPhoneNumber() != null)
            user.setPhone_number(requestDto.getPhoneNumber());
        if (requestDto.getEmail() != null)
            user.setEmail(requestDto.getEmail());
        if (requestDto.getName() != null)
            user.setNickname(requestDto.getNickname());
        if (requestDto.getBio() != null)
            user.setBio(requestDto.getBio());

        return new ResponseUserDto(profileRepository.save(user));
    }
}
