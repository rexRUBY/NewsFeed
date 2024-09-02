package com.sparta.newsfeed.post.service;

import com.sparta.newspeed.post.dto.PostRequestDto;
import com.sparta.newspeed.post.dto.PostResponseDto;
import com.sparta.newspeed.post.entity.Post;
import com.sparta.newspeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto savePost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getContents(),
                postRequestDto.getImgUrl());

        Post savedPost = postRepository.save(post);

    }
}
