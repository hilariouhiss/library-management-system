package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Response<List<User>> queryUserById() {
        List<User> users = userService.queryAllUsers();
        return Response.success("查询成功", users);
    }

    @DeleteMapping("/user/delete/{id}")
    public Response<Void> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return Response.success("删除成功");
    }

    @PutMapping("/user/update")
    public Response<User> updateUser(@RequestBody User user) {
        user = userService.updateUser(user);
        return Response.success("更新成功", user);
    }
}
