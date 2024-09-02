package com.sparta.newsfeed.post.controller;

import com.sparta.newsfeed.post.dto.commentDto.CommentRequestDto;
import com.sparta.newsfeed.post.dto.commentDto.CommentResponseDto;
import com.sparta.newsfeed.post.fix.Timestamped;
import com.sparta.newsfeed.post.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/posts")
public class CommentController extends Timestamped {
    private final CommentService commentService;

    @PostMapping("/{postsId}/comments")
    public ResponseEntity<CommentResponseDto> saveComments(@PathVariable Long postsId,
                                                           @RequestBody CommentRequestDto commentRequestDto,
                                                           HttpServletRequest res) {
        return ResponseEntity.ok(commentService.saveComments(postsId, commentRequestDto, res));
    }

    @GetMapping("/{postsId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable Long postsId) {
        return ResponseEntity.ok(commentService.getCommentList(postsId));
    }

    @PutMapping("/{postsId}/comments/{commentsId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postsId,
                                                            @PathVariable Long commentsId,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            HttpServletRequest res) {
        return ResponseEntity.ok(commentService.updateComment(postsId, commentsId, commentRequestDto, res));
    }

    @DeleteMapping("/{postsId}/comments/{commentsId}")
    public void deleteComment(@PathVariable Long postsId,
                              @PathVariable Long commentsId,
                              HttpServletRequest res) {
        commentService.deleteComment(postsId, commentsId, res);
    }

    @PostMapping("/{postsId}/comments/{commentsId}/likes")
    public ResponseEntity<CommentResponseDto> likeComment(@PathVariable Long postsId,
                                                          @PathVariable Long commentsId,
                                                          HttpServletRequest res) {
        return ResponseEntity.ok(commentService.likeComment(postsId, commentsId, res));
    }

    @DeleteMapping("/{postsId}/comments/{commentsId}/likes/{likesId}")
    public void deleteLikeComment(@PathVariable Long postsId,
                                  @PathVariable Long commentsId,
                                  @PathVariable Long likesId,
                                  HttpServletRequest res) {
        commentService.deleteLikeComment(postsId, commentsId, likesId, res);
    }
}
