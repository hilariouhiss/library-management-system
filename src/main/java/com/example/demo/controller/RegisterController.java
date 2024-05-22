package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@RestController
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Response<User> register(@RequestBody User user) {
        user = userService.addUser(user);
        return Response.success("注册成功", user);
    }
}
