package com.toy.springboard.springboard.post.service;

import com.toy.springboard.springboard.exception.user.UserException;
import com.toy.springboard.springboard.post.dto.PostRequest;
import com.toy.springboard.springboard.post.dto.PostResponse;
import com.toy.springboard.springboard.post.entity.Post;
import com.toy.springboard.springboard.post.repository.PostRepository;
import com.toy.springboard.springboard.user.entity.User;
import com.toy.springboard.springboard.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImp implements PostService{
    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
        return PostResponse.fromEntity(savePost);
    }

    @Override
    public Post getPost(Long id) {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return List.of();
    }

    @Override
    public Post updatePost(Long id, Post updatedPost) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }
}
