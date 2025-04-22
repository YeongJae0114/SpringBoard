package com.toy.springboard.springboard.comment.controller;

import com.toy.springboard.springboard.auth.CustomUserDetails;
import com.toy.springboard.springboard.comment.dto.CommentListResponse;
import com.toy.springboard.springboard.comment.dto.CommentRequest;
import com.toy.springboard.springboard.comment.dto.CommentResponse;
import com.toy.springboard.springboard.comment.service.CommentService;
import com.toy.springboard.springboard.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                      @PathVariable Long postId,
                                                                      @RequestBody CommentRequest request) {
        CommentResponse response = commentService.createComment(userDetails.getUser().getId(), postId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 게시글의 댓글 전체 조회
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentListResponse>> getComments(@PathVariable Long postId) {
        CommentListResponse response = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 댓글 수정
    @PatchMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                      @PathVariable Long postId,
                                                                      @PathVariable Long commentId,
                                                                      @RequestBody CommentRequest request) {
        CommentResponse response = commentService.updateComment(userDetails.getUser().getId(), commentId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 댓글 삭제
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId) {
        commentService.deleteComment(userDetails.getUser().getId(), commentId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
