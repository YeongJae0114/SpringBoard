package com.toy.springboard.springboard.post.service;

import com.toy.springboard.springboard.post.dto.NextCursor;
import com.toy.springboard.springboard.post.dto.PostPageResponse;
import com.toy.springboard.springboard.post.dto.PostPageResponse2;
import com.toy.springboard.springboard.post.dto.PostRequest;
import com.toy.springboard.springboard.post.dto.PostResponse;
import java.time.LocalDateTime;


public interface PostService {
    PostResponse createPost(Long userId, PostRequest request);

    PostResponse getPost(Long id);
    PostPageResponse findPostsByCursor(LocalDateTime createdDateCursor, Long cursorId);
    PostPageResponse2 findPostsByOffset(int page, int size);



    PostResponse updatePost(Long userId,Long id, PostRequest updatedPost);

    void deletePost(Long userId, Long id);
}
