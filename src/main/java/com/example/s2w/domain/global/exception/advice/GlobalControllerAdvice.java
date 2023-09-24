package com.example.s2w.domain.global.exception.advice;

import com.example.s2w.domain.global.exception.handler.CommonExceptionHandler;
import com.example.s2w.domain.global.response.ErrorCode;
import com.example.s2w.domain.global.response.Result;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="INTERNAL_SERVER_ERROR")
    protected void handleException(HttpServletRequest request, Exception e) throws IOException {
        e.printStackTrace();
        log.error("handleEntityNotFoundException", e);
    }

    @ExceptionHandler(CommonExceptionHandler.class)
    public ResponseEntity<Result> commonExceptionHandler(CommonExceptionHandler e) {
        log.error("commonExceptionHandler", e);
        return ResponseEntity.status(ErrorCode.of(e.getMessage()).getStatus())
                             .body(Result.errorResult(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity error(ConstraintViolationException e) {
        return ResponseEntity.ok().body(Result.errorResult(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
