package com.toy.springboard.springboard.post.repository;

import com.toy.springboard.springboard.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user
        WHERE p.id = :postId
        """)
    Optional<Post> findPostWithUserById(@Param("postId") Long postId);

    @Query("""
        SELECT p FROM Post p
        WHERE (p.createdAt < :createdDate)
           OR (p.createdAt = :createdDate AND p.id < :cursorId)
        ORDER BY p.createdAt DESC, p.id DESC
    """)
    List<Post> findPostsByCursor(
            @Param("createdDate") LocalDateTime createdDate,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

}
