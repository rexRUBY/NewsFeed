package com.sparta.newsfeed.follower.repository;

import com.sparta.newsfeed.auth.entity.User;
import com.sparta.newsfeed.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Optional<User> findByEmail(String email);
}
