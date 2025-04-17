package com.toy.springboard.springboard.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends BaseException{
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
