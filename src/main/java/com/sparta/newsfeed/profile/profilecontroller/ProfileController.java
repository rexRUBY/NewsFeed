package com.sparta.newsfeed.profile.profilecontroller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.profileservice.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    //프로필 생성
//    @PostMapping("/create")
//    public String createprofile(RequestUserDto req){
//        try{
//           return profileService.createprofile(req);
//        }
//        catch(Exception e){
//            return e.getMessage();
//        }
//    }

    //프로필 조회 기능 : 다른 사용자의 프로필 조회 시, 민감한 정보는 표시되지 않는다.
    //원래는 세션 값으로 현재 사용자 확인을 해야하지만 간단히 구현하기 위해 RequestUserDto로 대체한다.
    //RequestUserDto의 id값 == 현재 사용자, search_id == 조회대상의 id
    @GetMapping("/getinfo")
    public ResponseUserDto getprofile(@Auth AuthUser authuser, @RequestParam Long search_id){
        return profileService.getprofile(authuser,search_id);
    }

    @PutMapping("/update")
    public String updateprofile(@Auth AuthUser authuser,
                                @RequestParam(required = false) String input_password,
                                @RequestParam(required = false) String edit_password,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String phone_number,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String nickname,
                                @RequestParam(required = false) String bio){
        try{
            return profileService.updateprofile(authuser,input_password,edit_password,name,phone_number,email,nickname,bio);
        }catch(Exception e){
            return e.getMessage();
        }

    }

}
