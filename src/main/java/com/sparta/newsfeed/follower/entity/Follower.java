package com.sparta.newsfeed.follower.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Follower { // 어떤 유저가 어떤 유저를 팔로우했는지 출력하는 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 테이블 자체의 데이터 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저 아이디, PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower; // 팔로우 대상 유저 아이디

    private boolean isFollowAbailable = true;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Status status = null; // 친구 신청 상태
//
//    public enum Status{ // 친구신청을 보냈을 때 상태
//        pending,
//        accepted,
//        rejected
//    }

    public Follower(User user, User follower) {
        this.user = user;
        this.follower = follower;
    }

    // 팔로워 여부
    public void changeFollowStatus() {
        this.isFollowAbailable = !isFollowAbailable;
    }
}
