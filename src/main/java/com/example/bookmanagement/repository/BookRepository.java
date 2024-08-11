package com.example.bookmanagement.repository;

import com.example.bookmanagement.entity.Book;
import com.example.bookmanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository  extends JpaRepository<Book, Long> {
    List<Book> findByCategory(Category category);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategoryAndTitleContainingIgnoreCase(Category category, String title);
    List<Book> findByCategoryAndAuthorContainingIgnoreCase(Category category, String author);
}
