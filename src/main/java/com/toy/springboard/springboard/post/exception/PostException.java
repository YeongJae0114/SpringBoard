package com.toy.springboard.springboard.post.exception;

import com.toy.springboard.springboard.global.exception.BusinessException;
import com.toy.springboard.springboard.global.exception.ErrorCode;


public class PostException extends BusinessException {
    public PostException(ErrorCode errorCode) {
        super(errorCode);
    }
    public static PostException postNotFound(){return new PostException(PostErrorCode.POST_NOT_FOUND);}
    public static PostException postAlreadyDelete(){return new PostException(PostErrorCode.POST_ALREADY_DELETED);}
    public static PostException postForbidden() {return new PostException(PostErrorCode.POST_FORBIDDEN);}

}
