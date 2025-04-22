package com.toy.springboard.springboard.post.dto;

import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.user.dto.UserInfo;
import com.toy.springboard.springboard.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private UserInfo userInfo;

    public static PostResponse formEntity(Post post, User user){
        UserInfo userInfo = UserInfo.formEntity(user);
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), userInfo);
    }
}
