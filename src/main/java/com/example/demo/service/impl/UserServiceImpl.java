package com.example.demo.service.impl;

import com.example.demo.Repository.BorrowJpaRepository;
import com.example.demo.Repository.UserJpaRepository;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务接口实现类
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final BorrowJpaRepository borrowJpaRepository;


    public UserServiceImpl(UserJpaRepository userJpaRepository,
                           PasswordEncoder passwordEncoder, BorrowJpaRepository borrowJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.borrowJpaRepository = borrowJpaRepository;
    }

    @Transactional
    @Override
    public User addUser(User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean existed = userJpaRepository
                .findByUsername(newUser.getUsername())
                .isPresent();
        if (existed) {
            throw new IllegalArgumentException("User already exists");
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole("USER");
        return userJpaRepository.save(newUser);
    }

    @Transactional
    @Override
    public void deleteUserById(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        if (!userJpaRepository.existsById(userId)) {
            throw new IllegalArgumentException("User does not exist");
        }
        // 事先删除借阅记录
        borrowJpaRepository.deleteByUserId(userId);
        userJpaRepository.deleteById(userId);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        return userJpaRepository.save(user);
    }

    @Override
    public List<User> queryAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }
}
