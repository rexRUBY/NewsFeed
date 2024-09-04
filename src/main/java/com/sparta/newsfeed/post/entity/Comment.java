package com.sparta.newsfeed.post.entity;

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
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_contents")
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "like")
    private Long like;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    public Long countLikes() {
        this.like = (long) this.likeList.size();
        return this.like;
    }

    public Comment(String commentContents, Post post, User user) {
        this.commentContents = commentContents;
        this.post = post;
        this.user = user;
    }

    public void update(String commentContents) {
        this.commentContents = commentContents;
    }
}
