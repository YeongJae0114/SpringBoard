package com.toy.springboard.springboard.exception.user;

import com.toy.springboard.springboard.global.exception.BusinessException;
import com.toy.springboard.springboard.global.exception.ErrorCode;

public class UserException extends BusinessException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
    public static UserException userNotFound() {return new UserException(UserErrorCode.USER_NOT_FOUND);}
    public static UserException emailExists() {return new UserException(UserErrorCode.EMAIL_ALREADY_EXISTS);}
}
