package com.toy.springboard.springboard.global.exception;

import static com.toy.springboard.springboard.global.exception.CommonErrorCode.SYSTEM_ERROR;

import com.toy.springboard.springboard.global.response.ApiResponse;
import com.toy.springboard.springboard.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BusinessException e) {
        log.warn("Business Exception 발생: {}", e.getMessage());

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.fail(new ErrorResponse(e.getCode(), e.getMessage())));
    }

    /**
     * 데이터베이스 예외 처리
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleDatabaseException(DataAccessException e) {
        log.error("Database Error 발생", e); // ERROR 레벨
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(new ErrorResponse(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMessage())));
    }

    /**
     * 정의하지 않은 모든 예외를 서버 오류로 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception e) {
        log.error("System Exception 발생", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(new ErrorResponse(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getMessage())));
    }
}
