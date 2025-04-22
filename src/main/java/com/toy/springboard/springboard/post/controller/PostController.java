package com.toy.springboard.springboard.post.controller;

import com.toy.springboard.springboard.auth.CustomUserDetails;
import com.toy.springboard.springboard.global.response.ApiResponse;
import com.toy.springboard.springboard.post.dto.PostPageResponse;
import com.toy.springboard.springboard.post.dto.PostPageResponse2;
import com.toy.springboard.springboard.post.dto.PostRequest;
import com.toy.springboard.springboard.post.dto.PostResponse;
import com.toy.springboard.springboard.post.service.PostService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // post 생성
    @PostMapping("/api/post")
    public ResponseEntity<ApiResponse<PostResponse>> create(@RequestBody PostRequest postRequest,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = customUserDetails.getUser().getId();
        PostResponse response = postService.createPost(userId, postRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // post 단일 조회
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostOne(@PathVariable Long postId){
        PostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // post 커서 기반 조회
    @GetMapping("/api/post/cursor")
    public ResponseEntity<ApiResponse<PostPageResponse>> getPostCursor(@RequestParam(required = false) @DateTimeFormat LocalDateTime createdDateCursor,
                                                                   @RequestParam(required = false) Long cursorId){
        PostPageResponse response = postService.findPostsByCursor(createdDateCursor, cursorId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // post offset 기반 조회
    @GetMapping("/api/post/offset")
    public ResponseEntity<ApiResponse<PostPageResponse2>> getPostOffset(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        PostPageResponse2 response = postService.findPostsByOffset(page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


    // post 수정
    @PatchMapping("/api/post/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long postId,
                                                                @RequestBody PostRequest postRequest,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails){
        PostResponse response = postService.updatePost(postId, userDetails.getUser().getId(), postRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // post 삭제
    @DeleteMapping("/api/post/{postId}")
    public  ResponseEntity<ApiResponse<Void>>deletePost(@PathVariable Long postId,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails){
        postService.deletePost(userDetails.getUser().getId(), postId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
