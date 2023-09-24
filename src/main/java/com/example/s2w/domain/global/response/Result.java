package com.example.s2w.domain.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Result<T> {
    private Code code;
    private String message;
    private T data;

    @Builder
    public Result(Code code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result errorResult(String message) {
        return Result.builder()
                .code(Code.ERROR)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> Result successResult(T data) {
        return Result.builder()
                .code(Code.SUCCESS)
                .message("")
                .data(data)
                .build();
    }

}
