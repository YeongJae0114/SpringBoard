package com.toy.springboard.springboard.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();

    int getCode();

    String getMessage();
}
