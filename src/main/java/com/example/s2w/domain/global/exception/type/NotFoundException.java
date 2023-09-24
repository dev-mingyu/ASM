package com.example.s2w.domain.global.exception.type;

import com.example.s2w.domain.global.exception.handler.CommonExceptionHandler;
import com.example.s2w.domain.global.response.ErrorCode;

public class NotFoundException extends CommonExceptionHandler {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
