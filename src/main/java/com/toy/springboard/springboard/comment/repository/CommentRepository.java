package com.toy.springboard.springboard.comment.repository;

import com.toy.springboard.springboard.comment.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
    SELECT c FROM Comment c
    JOIN FETCH c.user
    WHERE c.post.id = :postId
    ORDER BY c.createdAt ASC
    """)
    List<Comment> findCommentsWithUserByPostId(@Param("postId") Long postId);

    @Query("""
    SELECT c FROM Comment c
    JOIN FETCH c.user
    WHERE c.id = :commentId
    """)
    Optional<Comment> findCommentWithUserById(@Param("commentId") Long commentId);
}
