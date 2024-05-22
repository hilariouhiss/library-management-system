package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
public interface UserService extends UserDetailsService {

    User addUser(User user);

    void deleteUserById(Integer Id);

    User updateUser(User user);

    List<User> queryAllUsers();
}
