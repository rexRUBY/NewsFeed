package com.sparta.newsfeed.post.fix;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Follower> follower = new ArrayList<>();
}
