package com.example.redistest.config;

import lombok.Data;

@Data
public class Result<T> {
    String code;
    String message;
    T data;

    public Result(String code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> ok(T data){
        return ok("success", data);
    }

    public static <T> Result<T> ok(String message, T data){
        return new Result("0", message, data);
    }

    public static <T> Result<T> fail(String message, T data){
        return new Result("1", message, data);
    }

    public static <T> Result<T> fail(T data){
        return fail("failed", data);
    }

}
