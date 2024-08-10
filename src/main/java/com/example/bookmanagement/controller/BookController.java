package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.entity.Book;
import com.example.bookmanagement.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<CustomResponse<List<BookDTO>>> getAllBooks() {
        List<BookDTO> titles = bookRepository.findAll()
                .stream()
                .map(book -> new BookDTO(book.getTitle(), book.getAuthor()))
                .collect(Collectors.toList());

        CustomResponse<List<BookDTO>> response = new CustomResponse<>(titles, "Books fetched successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        Book savedBook = bookRepository.save(book);
        BookDTO responseDTO = new BookDTO(savedBook.getTitle(), savedBook.getAuthor());
        CustomResponse<BookDTO> response = new CustomResponse<>(responseDTO, "Book created successfully", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<BookDTO>> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getAuthor());
        CustomResponse<BookDTO> response = new CustomResponse<>(bookDTO, "Book fetched successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<BookDTO>> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        Book updatedBook = bookRepository.save(book);
        BookDTO responseDTO = new BookDTO(updatedBook.getTitle(), updatedBook.getAuthor());
        CustomResponse<BookDTO> response = new CustomResponse<>(responseDTO, "Book updated successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
        CustomResponse<Void> response = new CustomResponse<>(null, "Book deleted successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public static class CustomResponse<T> {
        private T data;
        private String message;
        private int status;

        public CustomResponse(T data, String message, int status) {
            this.message = message;
            this.status = status;
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
