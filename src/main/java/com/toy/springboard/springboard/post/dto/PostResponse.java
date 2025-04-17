package com.toy.springboard.springboard.post.dto;

import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private String title;
    private String content;

    public static PostResponse fromEntity(Post post){
        return new PostResponse(post.getTitle(), post.getContent());
    }
}
