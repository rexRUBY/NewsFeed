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
    //searchId == 조회대상의 id
    @GetMapping("/getinfo")
    public ResponseUserDto getprofile(@Auth AuthUser authuser, @RequestParam Long searchId){
        return profileService.getprofile(authuser,searchId);
    }

    @PutMapping("/update")
    public String updateprofile(@Auth AuthUser authuser,
                                @RequestParam(required = false) String input_password,
                                @RequestParam(required = false) String edit_password,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String phone_number,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String nickname,
                                @RequestParam(required = false) String bio,
                                @RequestParam(required= false) String imageUrl){
        try{
            return profileService.updateprofile(authuser,input_password,edit_password,name,phone_number,email,nickname,bio,imageUrl);
        }catch(Exception e){
            return e.getMessage();
        }

    }

}
