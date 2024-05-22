package com.example.demo.controller;

import com.example.demo.entity.Borrow;
import com.example.demo.service.BorrowService;
import com.example.demo.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 借阅接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@RestController
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping("/borrow/add")
    public Response<Void> addBorrow(@RequestParam Integer userId, @RequestParam Integer bookId) {
        borrowService.addBorrow(userId, bookId);
        return Response.success("借阅成功");
    }

    @GetMapping("/borrow/return/{borrowId}")
    public Response<Void> returnBook(@PathVariable Integer borrowId) {
        borrowService.returnBook(borrowId);
        return Response.success("归还成功");
    }

    @GetMapping("/borrow/get/{id}")
    public Response<Borrow> queryBorrowById(@PathVariable Integer id) {
        Borrow borrow = borrowService.queryBorrowById(id);
        return Response.success("查询成功", borrow);
    }

    @GetMapping("/borrow/get/userId/{userId}")
    public Response<List<Borrow>> queryBorrowByUserId(@PathVariable Integer userId) {
        List<Borrow> borrows = borrowService.queryBorrowByUserId(userId);
        return Response.success("查询成功", borrows);
    }

    @DeleteMapping("/borrow/delete/{borrowId}")
    public Response<Void> deleteBorrow(@PathVariable Integer borrowId) {
        borrowService.deleteBorrow(borrowId);
        return Response.success("删除成功");
    }
}
