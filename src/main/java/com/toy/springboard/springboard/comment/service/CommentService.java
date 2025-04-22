package com.toy.springboard.springboard.comment.service;

import com.toy.springboard.springboard.comment.dto.CommentListResponse;
import com.toy.springboard.springboard.comment.dto.CommentRequest;
import com.toy.springboard.springboard.comment.dto.CommentResponse;
import java.util.List;

public interface CommentService {
    // 댓글 작성
    CommentResponse createComment(Long userId, Long postId, CommentRequest request);

    // 게시글에 달린 댓글 전체 조회
    CommentListResponse getCommentsByPost(Long postId);

    // 댓글 수정
    CommentResponse updateComment(Long userId, Long commentId, CommentRequest request);

    // 댓글 삭제
    void deleteComment(Long userId, Long commentId);
}
