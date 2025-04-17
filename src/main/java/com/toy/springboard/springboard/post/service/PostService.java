package com.toy.springboard.springboard.post.service;

import com.toy.springboard.springboard.post.dto.PostRequest;
import com.toy.springboard.springboard.post.dto.PostResponse;
import com.toy.springboard.springboard.post.entity.Post;
import java.util.List;

public interface PostService {
    PostResponse createPost(Long userId, PostRequest request);

    Post getPost(Long id);
    List<Post> getAllPosts();

    Post updatePost(Long id, Post updatedPost);
    void deletePost(Long id);
}
