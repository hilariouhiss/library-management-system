package com.example.demo.Repository;

import com.example.demo.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * 借阅持久层接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
public interface BorrowJpaRepository extends JpaRepository<Borrow, Integer> {

    Optional<List<Borrow>> findByUserId(Integer userId);

    Optional<Borrow> findByUserIdAndBookId(Integer userId, Integer bookId);

    @Query(value = "delete from hiss_db.t_borrow where user_id = ?1", nativeQuery = true)
    void deleteByUserId(Integer userId);

}
