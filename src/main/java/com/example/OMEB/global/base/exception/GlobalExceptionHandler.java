package com.example.OMEB.global.base.exception;

import com.example.OMEB.global.base.dto.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.OMEB.global.base.dto.FailedResponseBody.createFailureResponse;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class) // custom 에러
    public ResponseEntity<ResponseBody<Void>> handleServiceException(HttpServletRequest request, ServiceException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(createFailureResponse(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valid
    public ResponseEntity<ResponseBody<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(createFailureResponse(ErrorCode.INVALID_INPUT_VALUE, errorMessage));
    }

    @ExceptionHandler(AccessDeniedException.class) // 권한
    public ResponseEntity<ResponseBody<Void>> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(ErrorCode.ACCESS_DENIED.getStatus())
                .body(createFailureResponse(ErrorCode.ACCESS_DENIED));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> handleException(HttpServletRequest request, Exception e) {
        log.error("500 Exception URL : {} , ExceptionClass : {} , ExceptionMessage {}" , e.getMessage(),e.getClass(),e.getMessage());
        return ResponseEntity.internalServerError()
            .body(createFailureResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
