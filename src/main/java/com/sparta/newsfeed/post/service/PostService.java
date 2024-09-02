package com.sparta.newsfeed.post.service;

import com.sparta.newsfeed.post.dto.PostRequestDto;
import com.sparta.newsfeed.post.dto.PostResponseDto;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.exception.AuthorizedCheckException;
import com.sparta.newsfeed.post.fix.Follower;
import com.sparta.newsfeed.post.fix.User;
import com.sparta.newsfeed.post.fix.UserRepository;
import com.sparta.newsfeed.post.jwt.JwtUtil;
import com.sparta.newsfeed.post.repository.PostRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //jwt에서 유저이메일로 유저 찾기
    public User findUser(HttpServletRequest res){
        Claims authCheck = jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(jwtUtil.getTokenFromRequest(res)));
        User user = userRepository.findByEmail(authCheck.getSubject()).orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        return user;
    }
    @Transactional
    public PostResponseDto savePost(PostRequestDto postRequestDto, HttpServletRequest res) {
        User user = findUser(res);
        Post post = new Post(postRequestDto.getContents(),
                postRequestDto.getImgUrl(),
                user);
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> dtoList = new ArrayList<>();
        for(Post p : postList){
            PostResponseDto dto = new PostResponseDto(p);
            dtoList.add(dto);
        }
        return  dtoList;
    }

    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest res) {
        User user = findUser(res);
        Post post = postRepository.findById(id).orElseThrow(NullPointerException::new);
        if(post.getUser().equals(user)){
            post.update(postRequestDto.getContents(),
                    postRequestDto.getImgUrl());
        }else{
            throw new AuthorizedCheckException("not allowed");
        }
        return new PostResponseDto(post);
    }

    public void deletePost(Long id, HttpServletRequest res) {
        User user = findUser(res);
        Post post = postRepository.findById(id).orElseThrow(NullPointerException::new);
        if(post.getUser().equals(user)){
            postRepository.delete(post);
        }else{
            throw new AuthorizedCheckException("not allowed");
        }
    }

    public Page<PostResponseDto> getPost(int page, int size,HttpServletRequest res) {
        User user = findUser(res);
        List<Follower> followerList = user.getFollower();
        List<User> userList = new ArrayList<>();
        for(Follower f:followerList){
            userList.add(f.getFollower());
        }
        userList.add(user);
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Post> posts = postRepository.findByUserOrderByCreatedAtModifiedAtDesc(userList,pageable);

        return posts.map(PostResponseDto::new);
    }
}
