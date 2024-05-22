package com.example.demo.service.impl;

import com.example.demo.Repository.BookJpaRepository;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图书服务实现类
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    private final BookJpaRepository bookJpaRepository;

    public BookServiceImpl(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Transactional
    @Override
    public Book addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }
        if (bookJpaRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Book already exists");
        }
        return bookJpaRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBookById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        bookJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }
        if (bookJpaRepository.findById(book.getId()).isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }
        return bookJpaRepository.save(book);
    }

    @Override
    public List<Book> queryBookByTitleOrISBN(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }
        return bookJpaRepository.findByTitleOrIsbn(input).orElseThrow(
                () -> new IllegalArgumentException("Book not found")
        );
    }

    @Override
    public List<Book> queryAllBooks() {
        return bookJpaRepository.findAll();
    }
}
