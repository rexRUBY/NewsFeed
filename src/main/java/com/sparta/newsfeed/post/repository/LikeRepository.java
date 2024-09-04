package com.sparta.newsfeed.post.repository;

import com.sparta.newsfeed.post.entity.Comment;
import com.sparta.newsfeed.post.entity.Like;
import com.sparta.newsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findByPost(Post post);

    List<Like> findByComment(Comment comment);
}
