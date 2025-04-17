package com.toy.springboard.springboard.post.repository;

import com.toy.springboard.springboard.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
