package com.toy.springboard.springboard.post.controller;

import com.toy.springboard.springboard.auth.CustomUserDetails;
import com.toy.springboard.springboard.global.response.ApiResponse;
import com.toy.springboard.springboard.post.dto.PostRequest;
import com.toy.springboard.springboard.post.dto.PostResponse;
import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("api/post")
    public ResponseEntity<ApiResponse<PostResponse>> create(@RequestBody PostRequest postRequest,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = customUserDetails.getUser().getId();
        PostResponse response = postService.createPost(userId, postRequest);

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
