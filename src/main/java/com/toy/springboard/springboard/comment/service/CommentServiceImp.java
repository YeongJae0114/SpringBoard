package com.toy.springboard.springboard.comment.service;

import com.toy.springboard.springboard.comment.dto.CommentListResponse;
import com.toy.springboard.springboard.comment.dto.CommentRequest;
import com.toy.springboard.springboard.comment.dto.CommentResponse;
import com.toy.springboard.springboard.comment.entity.Comment;
import com.toy.springboard.springboard.comment.exception.CommentException;
import com.toy.springboard.springboard.comment.repository.CommentRepository;
import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.post.exception.PostException;
import com.toy.springboard.springboard.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImp implements CommentService{
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponse createComment(Long userId, Long postId, CommentRequest request) {
        Post post = postRepository.findPostWithUserById(postId).orElseThrow(PostException::postNotFound);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .build();
        comment.assignPost(post);
        comment.assignUser(post.getUser());

        commentRepository.save(comment);
        return CommentResponse.formEntity(comment);
    }

    @Override
    public CommentListResponse getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findCommentsWithUserByPostId(postId);

        if (comments.isEmpty()){
            throw CommentException.commentNotFound();
        }

        return CommentListResponse.formEntity(comments);
    }

    @Override
    public CommentResponse updateComment(Long userId, Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findCommentWithUserById(commentId)
                .orElseThrow(CommentException::commentNotFound);

        return CommentResponse.formEntity(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findCommentWithUserById(commentId)
                .orElseThrow(CommentException::commentNotFound);
        commentRepository.delete(comment);
    }
}
