package com.example.demo.service.impl;

import com.example.demo.Repository.BookJpaRepository;
import com.example.demo.Repository.BorrowJpaRepository;
import com.example.demo.Repository.UserJpaRepository;
import com.example.demo.entity.Book;
import com.example.demo.entity.Borrow;
import com.example.demo.service.BorrowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 借阅服务实现类
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@Service("borrowService")
public class BorrowServiceImpl implements BorrowService {

    private final BorrowJpaRepository borrowJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final BookJpaRepository bookJpaRepository;

    public BorrowServiceImpl(BorrowJpaRepository borrowJpaRepository,
                             UserJpaRepository userJpaRepository,
                             BookJpaRepository bookJpaRepository) {
        this.borrowJpaRepository = borrowJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
    }

    @Transactional
    @Override
    public void addBorrow(Integer userId, Integer bookId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        if (bookId == null) {
            throw new IllegalArgumentException("BookId is null");
        }

        Book book = bookJpaRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book does not exist"));

        if (book.getQuantity() <= 0) {
            throw new IllegalArgumentException("Book is out of stock");
        }

        Borrow borrow = borrowJpaRepository.findByUserIdAndBookId(userId, bookId)
                .filter(b -> b.getStatus() == 1)
                .map(b -> {
                    b.setStatus(0);
                    b.setBorrowDate(LocalDate.now());
                    b.setReturnDate(null);
                    return b;
                })
                .orElseGet(() -> {
                    Borrow newBorrow = new Borrow();
                    newBorrow.setStatus(0);
                    newBorrow.setBorrowDate(LocalDate.now());
                    return newBorrow;
                });

        userJpaRepository.findById(userId)
                .ifPresentOrElse(user -> {
                            borrow.setUser(user);
                            borrow.setBook(book);
                        },
                        () -> {
                            throw new IllegalArgumentException("User does not exist");
                        }
                );

        book.setQuantity(book.getQuantity() - 1);
        bookJpaRepository.save(book);
        borrowJpaRepository.save(borrow);
    }


    @Transactional
    @Override
    public void returnBook(Integer borrowId) {
        if (borrowId == null) {
            throw new IllegalArgumentException("BorrowId is null");
        }

        Borrow borrow = borrowJpaRepository.findById(borrowId)
                .orElseThrow(() -> new IllegalArgumentException("Borrow does not exist"));

        if (borrow.getStatus() != 0) {
            throw new IllegalArgumentException("Book has already been returned");
        }

        Book book = borrow.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookJpaRepository.save(book);

        borrow.setReturnDate(LocalDate.now());
        borrow.setStatus(1);
        borrowJpaRepository.save(borrow);
    }

    @Override
    public Borrow queryBorrowById(Integer borrowId) {
        if (borrowId == null) {
            throw new IllegalArgumentException("BorrowId is null");
        }
        if (borrowJpaRepository.findById(borrowId).isEmpty()) {
            throw new IllegalArgumentException("Borrow does not exist");
        }
        return borrowJpaRepository.findById(borrowId).get();
    }

    @Override
    public List<Borrow> queryBorrowByUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        if (borrowJpaRepository.findByUserId(userId).isEmpty()) {
            throw new IllegalArgumentException("Borrow does not exist");
        }
        return borrowJpaRepository.findByUserId(userId).get();
    }

    @Transactional
    @Override
    public void deleteBorrow(Integer borrowId) {
        if (borrowId == null) {
            throw new IllegalArgumentException("BorrowId is null");
        }
        if (borrowJpaRepository.findById(borrowId).isEmpty()) {
            throw new IllegalArgumentException("Borrow does not exist");
        }
        borrowJpaRepository.deleteById(borrowId);
    }
}
