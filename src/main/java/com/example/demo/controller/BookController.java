package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书类接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public Response<List<Book>> queryAllBooks() {
        return Response.success("查询成功", bookService.queryAllBooks());
    }

    @PostMapping("/book/add")
    public Response<Book> addBook(@RequestBody Book book) {
        book = bookService.addBook(book);
        return Response.success("添加成功", book);
    }

    @DeleteMapping("/book/delete/id/{id}")
    public Response<Book> deleteBook(@PathVariable Integer id) {
        bookService.deleteBookById(id);
        return Response.success("删除成功");
    }

    @PutMapping("/book/update")
    public Response<Book> updateBook(@RequestBody Book book) {
        book = bookService.updateBook(book);
        return Response.success("更新成功", book);
    }

    @GetMapping("/book/search/{input}")
    public Response<List<Book>> queryBookById(@PathVariable String input){
        List<Book> books = bookService.queryBookByTitleOrISBN(input);
        return Response.success("查询成功", books);
    }
}
