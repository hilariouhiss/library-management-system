package com.example.demo.Repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户持久层接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
