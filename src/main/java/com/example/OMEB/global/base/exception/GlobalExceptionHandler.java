package com.example.OMEB.global.base.exception;

import com.example.OMEB.global.base.dto.FailedResponseBody;
import com.example.OMEB.global.base.dto.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.OMEB.global.base.dto.FailedResponseBody.createFailureResponse;

@RestControllerAdvice
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
}
