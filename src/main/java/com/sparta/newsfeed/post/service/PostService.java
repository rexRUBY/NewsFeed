package com.sparta.newsfeed.post.service;



import com.sparta.newsfeed.post.fix.User;
import com.sparta.newsfeed.post.fix.UserRepository;
import com.sparta.newsfeed.post.dto.postDto.PostRequestDto;
import com.sparta.newsfeed.post.dto.postDto.PostResponseDto;
import com.sparta.newsfeed.post.entity.Like;
import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.exception.AuthorizedCheckException;
import com.sparta.newsfeed.post.fix.Follower;
import com.sparta.newsfeed.post.repository.LikeRepository;
import com.sparta.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    //id로 post찾기
    public Post findPost(Long postsId) {
        Post post = postRepository.findById(postsId).orElseThrow(NullPointerException::new);
        return post;
    }

    //id로 유저찾기
    public User findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        return user;
    }

    @Transactional
    public PostResponseDto savePost(PostRequestDto postRequestDto, Long userId) {
        User user = findUser(userId);
        Post post = new Post(postRequestDto.getContents(),
                postRequestDto.getImgUrl(),
                user);
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> dtoList = new ArrayList<>();
        for (Post p : postList) {
            PostResponseDto dto = new PostResponseDto(p);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, Long userId) {
        User user = findUser(userId);
        Post post = findPost(id);
        if (post.getUser().equals(user)) {
            post.update(postRequestDto.getContents(),
                    postRequestDto.getImgUrl());
        } else {
            throw new AuthorizedCheckException("not allowed");
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id, Long userId) {
        User user = findUser(userId);
        Post post = findPost(id);
        if (post.getUser().equals(user)) {
            postRepository.delete(post);
        } else {
            throw new AuthorizedCheckException("not allowed");
        }
    }

    public Page<PostResponseDto> getPost(int page, int size, Long userId) {
        User user = findUser(userId);
        List<Follower> followerList = user.getFollower();
        List<User> userList = new ArrayList<>();
        for (Follower f : followerList) {
            userList.add(f.getFollower());
        }
        userList.add(user);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(userList, pageable);

        return posts.map(PostResponseDto::new);
    }

    @Transactional
    public PostResponseDto likePost(Long id, Long userId) {
        User user = findUser(userId);
        Post post = findPost(id);
        List<Like> checkLike = likeRepository.findByPost(post);
        for (Like l : checkLike) {
            if (l.getUser().equals(user)) {
                throw new AuthorizedCheckException("already liked it");
            }
        }
        if (post.getUser().equals(user)) {
            throw new RuntimeException("not allowed to like yourself");
        }
        Like like = new Like(user, post);
        likeRepository.save(like);

        return new PostResponseDto(post);
    }

    //수정일 기준으로 페이지네이션
    public Page<PostResponseDto> getPostByModifiedAt(int page, int size, Long userId) {
        User user = findUser(userId);
        List<Follower> followerList = user.getFollower();
        List<User> userList = new ArrayList<>();
        for (Follower f : followerList) {
            userList.add(f.getFollower());
        }
        userList.add(user);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.findByUserInOrderByModifiedAtDesc(userList, pageable);

        return posts.map(PostResponseDto::new);
    }

    //like 수대로 페이지네이션
    public Page<PostResponseDto> getPostByLike(int page, int size, Long userId) {
        User user = findUser(userId);
        List<Follower> followerList = user.getFollower();
        List<User> userList = new ArrayList<>();
        for (Follower f : followerList) {
            userList.add(f.getFollower());
        }
        userList.add(user);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.findByUserInOrderByLikeDesc(userList, pageable);

        return posts.map(PostResponseDto::new);
    }

    //날짜사이 게시물 검색
    public Page<PostResponseDto> getPostByTime(LocalDateTime startDate, LocalDateTime endDate, int page, int size, Long userId) {
        User user = findUser(userId);
        List<Follower> followerList = user.getFollower();
        List<User> userList = new ArrayList<>();
        for (Follower f : followerList) {
            userList.add(f.getFollower());
        }
        userList.add(user);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.findByCreatedAtBetween(startDate, endDate, pageable);

        return posts.map(PostResponseDto::new);
    }

    @Transactional
    public void deleteLike(Long postsid, Long likeid, Long userId) {
        User user = findUser(userId);
        Post post = findPost(postsid);
        Like like = likeRepository.findById(likeid).orElseThrow(NullPointerException::new);
        if (like.getPost().equals(post) && like.getPost().getUser().equals(user)) {
            postRepository.delete(post);
        } else {
            throw new AuthorizedCheckException("not allowed");
        }
    }
}
