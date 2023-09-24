package com.example.s2w.domain.global.response;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    INVALID_REQUEST(400, "INVALID-ERR-400", "INVALID REQUEST"),
    NOT_FOUNT_SEED_ID(400, "SEED-ERR-400", "NOT FOUND SEED_ID"),

    ;

    private int status;
    private String errorCode;
    private String message;

    public static ErrorCode of(String message) {
        return Arrays.stream(values())
                     .filter(v -> message.equals(v.message))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException(ErrorCode.INTER_SERVER_ERROR.getMessage()));
    }

}
