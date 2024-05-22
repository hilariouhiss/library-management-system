package com.example.demo.Repository;

import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 图书持久层接口
 *
 * @author Liu Haiyang
 * @version 5/20/2024
 */
@Repository
public interface BookJpaRepository extends JpaRepository<Book, Integer> {

    @Query("select b from Book b where b.title = ?1 or b.isbn = ?1")
    Optional<List<Book>> findByTitleOrIsbn(String input);

    Optional<Book> findByTitle(String title);
}
