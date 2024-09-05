package com.sparta.newsfeed.profile.profileservice;

import com.sparta.newsfeed.auth.config.PasswordEncoder;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.profile.profilerepository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

//    public String createprofile(RequestUserDto req) {
//        String email = req.getEmail();
//        Optional<User> checkemail = profileRepository.findByEmail(email);
//        if (checkemail.isPresent()) {
//            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
//        }
//        String password = req.getPassword();
//        password = passwordEncoder.encode(password);
//
//        String name = req.getName();
//        String phone_number = req.getPhone_number();
//        Gender gender = req.getGender();
//        String nickname = req.getNickname();
//        String bio = req.getBio();
//
//        Date birthday = null;
//        if(req.getBirthday() != null) {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            try{
//                birthday = formatter.parse(req.getBirthday());
//            }catch (ParseException e){
//                throw new IllegalArgumentException("잘못된 날짜 형식입니다. 형식 : yyyy-MM-dd");
//            }
//        }
//
//
//        User user = new User(email, password, name, phone_number, gender,nickname, bio,birthday);
//        profileRepository.save(user);
//        return "등록 성공";
//
//    }
    //원래는 세션 값으로 현재 사용자 확인을 해야하지만 간단히 구현하기 위해 RequestUserDto로 대체한다.
    //RequestUserDto의 id값 == 현재 사용자, search_id == 조회대상의 id
    public ResponseUserDto getprofile(AuthUser authuser, Long search_id) {
        Long user_id;
        User search_user = profileRepository.findById(search_id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        if(authuser==null){
            // 로그인이 되어있지 않았다면 DB에 존재할 수 없는 값을 넣음
            user_id=-1L;
        }
        else{ //JWT로부터 로그인 정보를 받아왔지만 그 로그인한 사용자의 정보가 존재하지 않는다면 예외처리, 정상적으로 존재한다면 user_id에 로그인 사용자의 id를 담고 진행
            user_id=authuser.getId();
            User user = profileRepository.findById(user_id).orElseThrow(()->new IllegalArgumentException("잘못된 요청 입니다."));

        }

        ResponseUserDto res = new ResponseUserDto();
        
        //본인이 아닐 시 민감 정보 출력안함
        if(Objects.equals(user_id, search_id)){
            res.setName(search_user.getName());
            res.setPhone_number(search_user.getPhone_number());
        }
        res.setEmail(search_user.getEmail());
        res.setGender(search_user.getGender());
        res.setNickname(search_user.getNickname());
        res.setBio(search_user.getBio());
        res.setBirthday(search_user.getBirthday());

        return res;
    }

    //---------------------------------------------------------------------------------------------------------------------

    //프로필 수정
    public String updateprofile(AuthUser authuser, String input_password,String edit_password,String name,String phone_number,String email,String nickname,String bio) {
        //현재 로그인 사용자와 수정할 프로필의 사용자가 다르다면 예외를 발생시켜야 한다. 아직은 인증, 인가 시스템이 구현되어있지 않았음
        User user = profileRepository.findById(authuser.getId()).orElseThrow(()->new IllegalArgumentException("올바른 요청이 아닙니다."));

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

        if(name != null)
            user.setName(name);
        if(phone_number != null)
            user.setPhone_number(phone_number);
        if(email != null)
            user.setEmail(email);
        if(nickname != null)
            user.setNickname(nickname);
        if(bio != null)
            user.setBio(bio);
        profileRepository.save(user);

        return "수정 완료";
    }
}
