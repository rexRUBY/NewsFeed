package com.sparta.newsfeed.profile.profileservice;

import com.sparta.newsfeed.auth.config.PasswordEncoder;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.entity.User;
import com.sparta.newsfeed.profile.profilerepository.ProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;


import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    //대소문자 포함 + 특수문자, 숫자 하나 이상 포함된 영문 8글자 이상 비밀번호
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String UPLOAD_DIR = "uploads/profile_images/";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public ProfileService(ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseUserDto getprofile(AuthUser authuser, Long searchId) {
        Long user_id;
        User search_user = profileRepository.findById(searchId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자 입니다."));
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
        if(Objects.equals(user_id, searchId)){
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
    public String updateprofile(AuthUser authuser, String input_password,String edit_password,String name,String phone_number,String email,String nickname,String bio, String imageUrl) {
        //현재 로그인 사용자와 수정할 프로필의 사용자가 다르다면 예외를 발생시켜야 한다.
        User user = profileRepository.findById(authuser.getId()).orElseThrow(()->new IllegalArgumentException("올바른 요청이 아닙니다."));

        //인가가 되었다면

        //패스워드 수정 시 본인확인을 위해 비밀번호를 확인
        if(input_password != null && passwordEncoder.matches(input_password, user.getPassword())) {
            if(!isValidPassword(edit_password)){
                throw new IllegalArgumentException("하나 이상의 대소문자, 특수문자, 숫자를 포함해 8글자 이상이어야 합니다.");
            }

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
        if(imageUrl != null){
            System.out.println("Received imageUrl: " + imageUrl);
            try{
                uploadProfilePictureByUrl(user,imageUrl);
            }catch(Exception e){
                return e.getMessage();
            }

        }
        profileRepository.save(user);

        return "수정 완료";
    }

    public void uploadProfilePictureByUrl(User user, String imageUrl){
        try {
            // 이미지 URL에서 파일 확장자 가져오기
            String fileExtension = getFileExtensionFromUrl(imageUrl);

            // 유효한 확장자인지 확인 (필요시 추가적인 검증 가능)
            if (!isValidImageExtension(fileExtension)) {
                throw new Exception("올바른 확장자가 아닙니다.");
            }

            // 이미지 다운로드
            URL url = new URL(imageUrl);
            byte[] imageBytes = url.openStream().readAllBytes();

            // 디렉토리 생성
            Path uploadDir = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // 저장할 파일명 생성 (UUID 사용)
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            Path filePath = uploadDir.resolve(fileName);

            // 파일 저장
            Files.write(filePath, imageBytes);

            // 저장된 파일 경로를 URL로 변환
            String fileUrl = "/profile/image/" + fileName;

            // 사용자의 프로필 이미지 URL을 저장
            user.setProfileImageUrl(fileUrl);
            profileRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String getFileExtensionFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf(".") + 1).toLowerCase();
    }

    // 유효한 이미지 파일 확장자인지 확인하는 메서드
    private boolean isValidImageExtension(String extension) {
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }

    public static boolean isValidPassword(String password) {
        return pattern.matcher(password).matches();
    }
}
