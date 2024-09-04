package com.sparta.newsfeed.profile.entity;

import com.sparta.newsfeed.post.fix.Follower;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable=false,length=255)
    private String email;

    @Column(nullable=false,length=255)
    private String password;

    @Column(nullable=false,length=255)
    private String name;

    @Column(length=255)
    private String phone_number;

    @Column(length=50)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String bio;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "follower")
    private List<Follower> follower = new ArrayList<>();

    public User(String username, String password, String email) {
        this.name = username;
        this.password = password;
        this.email = email;
    }

    public User(String email,String password,String name,String phone_number,Gender gender,String nickname,String bio,Date date){
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone_number = phone_number;
        this.gender = gender;
        this.bio = bio;
        this.birthday = date;
        this.nickname = nickname;
    }


    @PrePersist
    public void prePersist() {
        if(this.nickname == null) {
            this.nickname = "defaultNickname";
        }
        if(this.gender == null) {
            this.gender = Gender.NONE;
        }
    }

}
