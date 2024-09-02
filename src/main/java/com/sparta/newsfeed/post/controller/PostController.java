package com.sparta.newsfeed.post.controller;

import com.sparta.newspeed.post.dto.PostRequestDto;
import com.sparta.newspeed.post.dto.PostResponseDto;
import com.sparta.newspeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok(postService.savePost(postRequestDto));
    }
}
