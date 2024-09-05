기능,method,진행상황,URL,request,response,Status Code
프로필 조회,POST,완료,/profile/getinfo,"requestparam
{
”searchId”:”1”
}","{    ""id"": “1”,    
     ""email"": ""test@sample.com"",         
     ""name"": “test”,    
     ""phoneNumber"": “01012345678,    
     ""nickname"": ""defaultNickname"",         
     ""bio"": “좋은 하루 되세요”,    
     ""birthday"": null
}",200 OK
프로필 수정,PUT,완료,/users/{userId}/pofile,"{    ""inputPassword"": ""1q2w3e4rT!"",    ""editPassword"": ""1q2w3e4rT!@"",    ""bio"": ""좋은 하루 되세요"",    ""birthday"": ""2002-07-02""}","{
""id"": 6,
""email"": ""mailto:test6dsf@sample.com"",
""name"": ""test7"",
""phoneNumber"": ""01012345ffdfs5679"",
""nickname"": ""defaultNickname"",
""bio"": ""좋은 하루 되세요"",
""birthday"": ""2002-07-02T00:00:00.000+00:00""
}",200 OK
게시물 작성,POST,완료,/users/posts,"{
""contents"": ""This is a new post."",
""imgUrl"": ""http://example.com/image.jpg""
}
","{
""id"": 1,
""userId"": 6,
""imgUrl"": ""http://example.com/image.jpg"",
""contents"": ""This is a new post.""
}",200 OK
게시물 조회,GET,완료,/users/posts,,"[
{
""id"": 1,
""userId"": 6,
""imgUrl"": ""http://example.com/image.jpg"",
""contents"": ""This is a new post.""
}
]",200 OK
게시물 수정,PUT,완료,/users/posts/{postid},"{  ""contents"": ""This is an updated post."",  ""imgUrl"": ""http://example.com/updated_image.jpg""}","{
""id"": 1,
""userId"": 6,
""imgUrl"": ""http://example.com/updated_image.jpg"",
""contents"": ""This is an updated post.""
}",200 OK
게시물 삭제,DELETE,완료,/users/posts/{postid},,,200 OK
뉴스피드 조회,GET,완료,/users/posts/news?page={page}&size={size},,"{    ""content"": [],    ""pageable"": {        ""pageNumber"": 0,        ""pageSize"": 10,        ""sort"": {            ""empty"": true,            ""sorted"": false,            ""unsorted"": true        },        ""offset"": 0,        ""paged"": true,        ""unpaged"": false    },    ""last"": true,    ""totalPages"": 0,    ""totalElements"": 0,    ""first"": true,    ""size"": 10,    ""number"": 0,    ""sort"": {        ""empty"": true,        ""sorted"": false,        ""unsorted"": true    },    ""numberOfElements"": 0,    ""empty"": true}",200 OK
뉴스피드조회수정일순,GET,완료,/users/posts/news/modified={page}&size={size},,"{    ""content"": [],    ""pageable"": {        ""pageNumber"": 0,        ""pageSize"": 10,        ""sort"": {            ""empty"": true,            ""sorted"": false,            ""unsorted"": true        },        ""offset"": 0,        ""paged"": true,        ""unpaged"": false    },    ""last"": true,    ""totalPages"": 0,    ""totalElements"": 0,    ""first"": true,    ""size"": 10,    ""number"": 0,    ""sort"": {        ""empty"": true,        ""sorted"": false,        ""unsorted"": true    },    ""numberOfElements"": 0,    ""empty"": true}",200 OK
뉴스피드like순,GET,완료,/users/posts/news/like?page={page}&size={size},,"{    ""content"": [],    ""pageable"": {        ""pageNumber"": 0,        ""pageSize"": 10,        ""sort"": {            ""empty"": true,            ""sorted"": false,            ""unsorted"": true        },        ""offset"": 0,        ""paged"": true,        ""unpaged"": false    },    ""last"": true,    ""totalPages"": 0,    ""totalElements"": 0,    ""first"": true,    ""size"": 10,    ""number"": 0,    ""sort"": {        ""empty"": true,        ""sorted"": false,        ""unsorted"": true    },    ""numberOfElements"": 0,    ""empty"": true}",200 OK
뉴스피드 시간검색,GET,완료,/users/posts/news/search?startDate={start-date}&endDate={end-date}&page={page}&size={size},,"{    ""content"": [],    ""pageable"": {        ""pageNumber"": 0,        ""pageSize"": 10,        ""sort"": {            ""empty"": true,            ""sorted"": false,            ""unsorted"": true        },        ""offset"": 0,        ""paged"": true,        ""unpaged"": false    },    ""last"": true,    ""totalPages"": 0,    ""totalElements"": 0,    ""first"": true,    ""size"": 10,    ""number"": 0,    ""sort"": {        ""empty"": true,        ""sorted"": false,        ""unsorted"": true    },    ""numberOfElements"": 0,    ""empty"": true}",200 OK
게시물 like,POST,완료,/users/posts/{postId}/like,,,200 OK
게시물 like 취소,DELETE,완료,/users/posts/{postId}/like/{likeId},,,200 OK
댓글생성,POST,완료,/users/posts/{postsId}/comments,,,200 OK
댓글 조회,GET,완료,/users/posts/{postsId}/comments,,,200 OK
댓글 수정,PUT,완료,/users/posts/{postsId}/comments/{commentsId},,,200 OK
댓글 삭제,DELETE,완료,/users/posts/{postsId}/comments/{commentsId},,,200 OK
댓글 like,POST,완료,/users/posts/{postsId}/comments/{commentsId}/likes,,,200 OK
댓글 like 취소,DELETE,완료,/users/posts/{postsId}/comments/{commentsId}/likes/{likesId},,,200 OK
회원가입,POST,완료,/users/signup,"requestparam
{
    ""name"" : ""example"",
    ""password"" : ""1234"",
    ""email"" : ""mailto:test@sample.com"",
    ""phoneNumber"" : ""010-1234-5678""
} ","{    
    ""id"": 1,    ""email"": ""test@sample.com"",                                                   
    ""name"": ""example"",    
    ""phone_number"": null,    
    ""nickname"": null,    
    ""bio"": null,    
    ""birthday"": null
}",200 OK
회원탈퇴,DELETE,완료,/users/withdrawal,"requestparam
{
    “password”:”Password1!”
}",,200 OK
로그인,POST,완료,/users/login,"{
    ""password"" : ""1234"",
    ""email"" : ""mailto:test@sample.com""
}","Set-Cookie: Authorization=Bearer%20eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzI1NTIzMTY4LCJpYXQiOjE3MjU1MTk1Njh9.BjYJdEa6luTGMxdT4GGvELEKBfNHaT7Eu1rdvI75MwM; Domain=localhost; Path=/; HttpOnly; Secure; SameSite=Lax
",200 OK
친구 추가,POST,완료,/users/followers,"{
         “userId” : 2468,
         “followerId” : 4680
}","{
	”follower” : ""otherUserId"",
	”user” : ""userId"",
	”status” : ""pending""
}",200 OK
친구 요청 수락,POST,완료,/users/followers/accept,"{
        ”email” : “test@sample.com”
}","{
        ”email” : “test@sample.com”,
        “status” : accepted”
}",200 OK
친구 요청 거절,DELETE,완료,/users/followers/denied,"{
        ”email” : “test@sample.com”
}",,200 OK
친구 요청 목록 조회,GET,완료,/users/followers,,"{
    {
           ""email"" : ""test1@sample.com""
     },
    {
            ""email"" : ""test2@example.com""
     }
}",200 OK
친구 끊기,DELETE,완료,/users/{userId}/followers/{followerId},"{
        ”email” : “test@sample.com”
}",,200 OK
