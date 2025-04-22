package com.toy.springboard.springboard.post.dto;

import com.toy.springboard.springboard.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostPageResponse {
    private List<PostResponse> postResponseList;
    private NextCursor nextCursor;
    private boolean hasNext;

    public static PostPageResponse formEntity(List<Post> posts, NextCursor nextCursor, boolean hasNext){
        NextCursor nextCursor2 = NextCursor.formEntity(nextCursor.getCreatedDateCursor(), nextCursor.getCursorId());
        List<PostResponse> postResponseList = posts.stream()
                .map(post -> PostResponse.formEntity(post, post.getUser()))
                .toList();

        return new PostPageResponse(postResponseList, nextCursor2, hasNext);
    }
}
