package com.sparta.newsfeed.post.entity;

import com.sparta.newsfeed.post.fix.Follower;
import com.sparta.newsfeed.post.fix.Timestamped;
import com.sparta.newsfeed.post.fix.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "contents")
    private String contents;

    @Column(name = "like")
    private Long like;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    public Post(String contents, String imgUrl,User user) {
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.user=user;
    }

    public void update(String contents, String imgUrl) {
        this.contents = contents;
        this.imgUrl = imgUrl;
    }
}
