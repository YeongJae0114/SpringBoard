package com.toy.springboard.springboard.post.service;

import com.toy.springboard.springboard.post.dto.NextCursor;
import com.toy.springboard.springboard.post.dto.PostPageResponse;
import com.toy.springboard.springboard.post.dto.PostPageResponse2;
import com.toy.springboard.springboard.post.exception.PostException;
import com.toy.springboard.springboard.user.exception.UserException;
import com.toy.springboard.springboard.post.dto.PostRequest;
import com.toy.springboard.springboard.post.dto.PostResponse;
import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.post.repository.PostRepository;
import com.toy.springboard.springboard.user.entity.User;
import com.toy.springboard.springboard.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImp implements PostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private static final int PAGE_SIZE = 10;

    @Override
    @Transactional
    public PostResponse createPost(Long userId, PostRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserException::userNotFound);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        post.assignUser(user);
        Post savePost = postRepository.save(post);
        return PostResponse.formEntity(savePost, user);
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findPostWithUserById(postId)
                .orElseThrow(PostException::postNotFound);

        return PostResponse.formEntity(post, post.getUser());
    }

    @Override
    public PostPageResponse findPostsByCursor(LocalDateTime createdDateCursor,Long cursorId) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE+1); // offset = 0, size 만큼 가져오기

        LocalDateTime cursorDate = createdDateCursor != null ? createdDateCursor : LocalDateTime.now();
        Long safeCursorId = cursorId != null ? cursorId : Long.MAX_VALUE;

        List<Post> postsByCursor = postRepository.findPostsByCursor(cursorDate, safeCursorId, pageable);

        boolean hasNext = postsByCursor.size() > PAGE_SIZE;

        List<Post> results = hasNext ? postsByCursor.subList(0, PAGE_SIZE) : postsByCursor;

        NextCursor nextCursor = null;
        if (!results.isEmpty()) {
            Post lastPost = results.get(results.size() - 1);
            nextCursor = new NextCursor(lastPost.getCreatedAt(), lastPost.getId());
        }

        assert nextCursor != null;
        return PostPageResponse.formEntity(results, nextCursor, hasNext);
    }

    @Override
    public PostPageResponse2 findPostsByOffset(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Post> postPage = postRepository.findAll(pageable); // offset 기반 조회

        int nextPage = postPage.hasNext() ? page + 1 : -1;

        return PostPageResponse2.formEntity(
                postPage.getContent(),
                nextPage,
                postPage.hasNext()
        );
    }



    @Override
    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostRequest updatedPost) {
        Post post = postRepository.findPostWithUserById(postId)
                .orElseThrow(PostException::postNotFound);

        if (!userId.equals(post.getUser().getId())){
            throw PostException.postForbidden();
        }

        Post updatePost = post.updatePost(updatedPost.getTitle(), updatedPost.getContent());
        return PostResponse.formEntity(updatePost, post.getUser());
    }

    @Override
    @Transactional
    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findPostWithUserById(postId)
                .orElseThrow(PostException::postNotFound);

        // 작성자 아님 → 예외
        if (!post.getUser().getId().equals(userId)) {
            throw PostException.postForbidden();
        }
        postRepository.delete(post);
    }
}
