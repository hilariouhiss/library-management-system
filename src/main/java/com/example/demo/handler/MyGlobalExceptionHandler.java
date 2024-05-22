package com.example.demo.handler;

import com.example.demo.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 *
 * @author Liu Haiyang
 * @version 5/21/2024
 */
@ControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return Response.error(400, e.getMessage());
    }
}
