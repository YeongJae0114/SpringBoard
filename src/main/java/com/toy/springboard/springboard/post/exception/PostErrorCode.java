package com.toy.springboard.springboard.post.exception;

import com.toy.springboard.springboard.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "해당 게시글이 존재하지 않습니다."),
    POST_ALREADY_DELETED(HttpStatus.CONFLICT, 2002, "이미 삭제된 게시글입니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, 2003, "해당 게시글에 대한 수정 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
