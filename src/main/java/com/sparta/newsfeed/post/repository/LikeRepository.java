package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
}
