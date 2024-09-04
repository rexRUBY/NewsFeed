package com.sparta.newsfeed.auth.repository;

import com.sparta.newsfeed.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
}