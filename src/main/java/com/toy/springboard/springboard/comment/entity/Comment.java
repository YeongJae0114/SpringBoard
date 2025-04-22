package com.toy.springboard.springboard.comment.entity;

import com.toy.springboard.springboard.global.common.BaseEntity;
import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 어떤 게시글에 달린 댓글인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 연관관계 편의 메서드
    public void assignUser(User user) {
        this.user = user;
        user.getComments().add(this);
    }

    public void assignPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    // 댓글 수정
    public void updateContent(String content) {
        this.content = content;
    }
}
