package com.toy.springboard.springboard.comment.exception;

import com.toy.springboard.springboard.global.exception.BusinessException;
import com.toy.springboard.springboard.global.exception.ErrorCode;

public class CommentException extends BusinessException {
    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
    public static CommentException commentNotFound(){return new CommentException(CommentErrorCode.COMMENT_NOT_FOUND);}
    public static CommentException commentAlreadyDelete(){return new CommentException(CommentErrorCode.COMMENT_ALREADY_DELETED);}
    public static CommentException commentForbidden(){return new CommentException(CommentErrorCode.COMMENT_FORBIDDEN);}
}
