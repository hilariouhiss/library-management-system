package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 请求响应类
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    public int status;
    public String message;
    public T data;

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(200, message, data);
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(200, message,null);
    }

    public static Response<Void> error(int status,String errorMessage) {
        return new Response<>(status, errorMessage, null);
    }
}
