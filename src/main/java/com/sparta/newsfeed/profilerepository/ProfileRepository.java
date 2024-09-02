package com.sparta.newsfeed.profilerepository;

import com.sparta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
