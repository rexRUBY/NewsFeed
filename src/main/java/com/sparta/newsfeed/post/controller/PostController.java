package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.fix.User;
import com.sparta.newsfeed.post.fix.UserRepository;
import com.sparta.newsfeed.post.jwt.JwtUtil;
import com.sparta.newsfeed.post.service.PostService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    /*//jwt에서 유저이메일로 유저 찾기
    public User findUser(HttpServletRequest res){
        Claims authCheck = jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(jwtUtil.getTokenFromRequest(res)));
        User user = userRepository.findByEmail(authCheck.getSubject()).orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        return user;
    }*/

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto postRequestDto,
                                                    HttpServletRequest res){
        return ResponseEntity.ok(postService.savePost(postRequestDto, res));
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<PostResponseDto> likePost(@RequestParam Long id,HttpServletRequest res){
        return ResponseEntity.ok(postService.likePost(id,res));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPostList(){
        return ResponseEntity.ok(postService.getPostList());
    }

    @GetMapping("/posts/news")
    public ResponseEntity<Page<PostResponseDto>> getPost(@RequestParam(defaultValue = "1")int page,
                                                         @RequestParam(defaultValue = "10")int size,
                                                         HttpServletRequest res){
        return ResponseEntity.ok(postService.getPost(page,size,res));
    }

    @GetMapping("/posts/news/modified")
    public ResponseEntity<Page<PostResponseDto>> getPostByModifiedAt(@RequestParam(defaultValue = "1")int page,
                                                         @RequestParam(defaultValue = "10")int size,
                                                         HttpServletRequest res){
        return ResponseEntity.ok(postService.getPostByModifiedAt(page,size,res));
    }

    @GetMapping("/posts/news/like")
    public ResponseEntity<Page<PostResponseDto>> getPostByLike(@RequestParam(defaultValue = "1")int page,
                                                         @RequestParam(defaultValue = "10")int size,
                                                         HttpServletRequest res){
        return ResponseEntity.ok(postService.getPostByLike(page,size,res));
    }

    @GetMapping("/posts/news/search")
    public ResponseEntity<Page<PostResponseDto>> getPostByTime(@RequestParam(defaultValue = "1")int page,
                                                               @RequestParam(defaultValue = "10")int size,
                                                               @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                               @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                               HttpServletRequest res){
        return ResponseEntity.ok(postService.getPostByTime(startDate,endDate,page,size,res));
    }


    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                     @RequestBody PostRequestDto postRequestDto,
                                                     HttpServletRequest res){
        return ResponseEntity.ok(postService.updatePost(id,postRequestDto,res));
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id, HttpServletRequest res){
        postService.deletePost(id, res);
    }


}
