package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Post;
import com.sparta.newsfeed.post.fix.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findByUserOrderByCreatedAtModifiedAtDesc(List<User> userList, Pageable pageable);
}
