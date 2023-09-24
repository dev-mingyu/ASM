package com.example.s2w.domain.global.exception.handler;

import com.example.s2w.domain.global.response.ErrorCode;

public class CommonExceptionHandler extends RuntimeException {

    public CommonExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public CommonExceptionHandler(String message) {
        super(message);
    }

}
