package com.toy.springboard.springboard.comment.exception;

import com.toy.springboard.springboard.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "해당 댓글이 존재하지 않습니다."),
    COMMENT_ALREADY_DELETED(HttpStatus.CONFLICT, 3002, "이미 삭제된 댓글입니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, 3003, "해당 댓글에 대한 수정/삭제 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
