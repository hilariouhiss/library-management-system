package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;

/**
 * 图书服务接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
public interface BookService {

    Book addBook(Book book);

    void deleteBookById(Integer id);

    Book updateBook(Book book);

    List<Book> queryBookByTitleOrISBN(String input);

    List<Book> queryAllBooks();
}
