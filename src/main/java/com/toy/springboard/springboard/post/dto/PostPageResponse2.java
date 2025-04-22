package com.toy.springboard.springboard.post.dto;

import com.toy.springboard.springboard.post.entity.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostPageResponse2 {
    private List<PostResponse> postResponseList;
    private int nextPage;
    private boolean hasNext;

    public static PostPageResponse2 formEntity(List<Post> posts, int nextPage, boolean hasNext){
        List<PostResponse> postResponseList = posts.stream()
                .map(post -> PostResponse.formEntity(post, post.getUser()))
                .toList();

        return new PostPageResponse2(postResponseList, nextPage, hasNext);
    }
}
