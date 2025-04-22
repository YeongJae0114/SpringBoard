package com.toy.springboard.springboard.comment.dto;

import com.toy.springboard.springboard.comment.entity.Comment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {
    private List<CommentResponse> comments;

    public static CommentListResponse formEntity(List<Comment> commentList) {
        List<CommentResponse> responses = commentList.stream()
                .map(CommentResponse::formEntity)
                .collect(Collectors.toList());

        return new CommentListResponse(responses);
    }
}
