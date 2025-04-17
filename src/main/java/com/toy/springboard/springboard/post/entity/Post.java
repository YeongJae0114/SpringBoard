package com.toy.springboard.springboard.post.entity;

import com.toy.springboard.springboard.global.common.BaseEntity;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // FK 컬럼명 지정
    private User user;

    public void assignUser(User user) {
        this.user = user;
        // 양방향 관계 설정
        if (!user.getPosts().contains(this)) {
            user.getPosts().add(this);
        }
    }
}
