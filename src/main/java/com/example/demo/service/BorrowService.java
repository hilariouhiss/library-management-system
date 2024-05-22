package com.example.demo.service;

import com.example.demo.entity.Borrow;

import java.util.List;

/**
 * 借阅归还服务
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
public interface BorrowService {

    void addBorrow(Integer userId,Integer bookId);

    void returnBook(Integer borrowId);

    Borrow queryBorrowById(Integer borrowId);

    List<Borrow> queryBorrowByUserId(Integer userId);

    void deleteBorrow(Integer borrowId);

}
