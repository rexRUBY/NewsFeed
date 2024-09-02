package com.sparta.newsfeed.follower.repository;

import com.sparta.newsfeed.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    boolean existsByUserAndFollower(User user, User follower);
}
