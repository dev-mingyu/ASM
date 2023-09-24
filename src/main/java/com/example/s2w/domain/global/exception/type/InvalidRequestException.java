package com.example.s2w.domain.global.exception.type;

import com.example.s2w.domain.global.exception.handler.CommonExceptionHandler;
import com.example.s2w.domain.global.response.ErrorCode;

public class InvalidRequestException extends CommonExceptionHandler {

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
