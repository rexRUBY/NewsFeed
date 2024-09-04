package com.sparta.newsfeed.follower.entity;

import com.sparta.newsfeed.auth.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Follower { // 어떤 유저가 어떤 유저를 팔로우했는지 출력하는 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 테이블 자체의 데이터 아이디

    @Column(name = "user_id")
    private Long userId; // 친구신청한 유저 아이디, PK

    @Column(name = "follower_id")
    private Long followerId; // 친구신청 받는 유저 아이디

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = null; // 친구 신청 상태

    public enum Status{ // 친구신청을 보냈을 때 상태
        pending,
        accepted
    }

    public Follower(User user, User follower) {
        this.userId = user.getId();
        this.followerId = follower.getId();
        this.status = Status.pending;
    }
}
