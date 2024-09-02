package com.sparta.newsfeed.profileservice;

import com.sparta.newsfeed.config.PasswordEncoder;
import com.sparta.newsfeed.dto.RequestUserDto;
import com.sparta.newsfeed.dto.ResponseUserDto;
import com.sparta.newsfeed.entity.Gender;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.profilerepository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String createprofile(RequestUserDto req) {
        String email = req.getEmail();
        Optional<User> checkemail = profileRepository.findByEmail(email);
        if (checkemail.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        }
        String password = req.getPassword();
        password = passwordEncoder.encode(password);

        String name = req.getName();
        String phone_number = req.getPhone_number();
        Gender gender = req.getGender();
        String nickname = req.getNickname();
        String bio = req.getBio();

        Date birthday = null;
        if(req.getBirthday() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try{
                birthday = formatter.parse(req.getBirthday());
            }catch (ParseException e){
                throw new IllegalArgumentException("잘못된 날짜 형식입니다. 형식 : yyyy-MM-dd");
            }
        }


        User user = new User(email, password, name, phone_number, gender,nickname, bio,birthday);
        profileRepository.save(user);
        return "등록 성공";

    }

    //원래는 세션 값으로 현재 사용자 확인을 해야하지만 간단히 구현하기 위해 RequestUserDto로 대체한다.
    //RequestUserDto의 id값 == 현재 사용자, search_id == 조회대상의 id
    public ResponseUserDto getprofile(RequestUserDto req, Long search_id) {
        User user = profileRepository.findById(req.getId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        ResponseUserDto res = new ResponseUserDto();
        
        //민감 정보 출력안함
        if(Objects.equals(req.getId(), search_id)){
            res.setName(user.getName());
            res.setPhone_number(user.getPhone_number());
        }
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setNickname(user.getNickname());
        res.setBio(user.getBio());
        res.setBirthday(user.getBirthday());

        return res;
    }

    //---------------------------------------------------------------------------------------------------------------------

    //프로필 수정
    //원래 세션값으로 인증과 인가를 해야하지만 지금은 기능 구현을 위해 RequestUserDto로 대체
    public String updateprofile(RequestUserDto req, String input_password,String edit_password) {
        //현재 로그인 사용자와 수정할 프로필의 사용자가 다르다면 예외를 발생시켜야 한다. 아직은 인증, 인가 시스템이 구현되어있지 않았음
        User user = profileRepository.findById(req.getId()).orElseThrow(()->new IllegalArgumentException("올바른 요청이 아닙니다."));

        //인가가 되었다면

        //패스워드 수정 시 본인확인을 위해 비밀번호를 확인
        if(input_password != null && passwordEncoder.matches(input_password, user.getPassword())) {
            if(input_password.equals(edit_password)) {
                throw new IllegalArgumentException("현재 비밀번호와 같은 비밀번호로 설정할 수 없습니다.");
            }
            user.setPassword(passwordEncoder.encode(edit_password));
        }
        else if(input_password != null && !passwordEncoder.matches(input_password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if(req.getName() != null)
            user.setName(req.getName());
        if(req.getPhone_number() != null)
            user.setPhone_number(req.getPhone_number());
        if(req.getEmail() != null)
            user.setEmail(req.getEmail());
        if(req.getNickname() != null)
            user.setNickname(req.getNickname());
        if(req.getBio() != null)
            user.setBio(req.getBio());
        profileRepository.save(user);

        return "수정 완료";
    }
}
