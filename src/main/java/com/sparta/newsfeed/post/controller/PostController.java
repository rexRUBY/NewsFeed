package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto postRequestDto,
                                                    HttpServletRequest res){
        return ResponseEntity.ok(postService.savePost(postRequestDto, res));
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
