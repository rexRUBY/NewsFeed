package com.sparta.newsfeed.follower.repository;

import com.sparta.newsfeed.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByUserIdAndStatus(Long userId, Follower.Status status);
    List<Follower> findAllByFollowerIdAndStatus(Long followerId, Follower.Status status);
    Optional<Follower> findByFollowerIdAndUserId(Long followerId, Long userId);
}

