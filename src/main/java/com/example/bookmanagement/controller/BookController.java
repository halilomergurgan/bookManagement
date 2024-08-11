package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.BookDTO;
import com.example.bookmanagement.entity.Book;
import com.example.bookmanagement.entity.Category;
import com.example.bookmanagement.exception.CustomResponse;
import com.example.bookmanagement.repository.BookRepository;
import com.example.bookmanagement.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<CustomResponse<List<BookDTO>>> getAllBooks() {
        List<BookDTO> titles = bookRepository.findAll()
                .stream()
                .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                .collect(Collectors.toList());

        CustomResponse<List<BookDTO>> response = new CustomResponse<>(titles, "Books fetched successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());

        if (bookDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(bookDTO.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found"));
            book.setCategory(category);
        }


        Book savedBook = bookRepository.save(book);
        BookDTO responseDTO = new BookDTO(savedBook.getTitle(), savedBook.getAuthor(), savedBook.getCategory().getId());
        CustomResponse<BookDTO> response = new CustomResponse<>(responseDTO, "Book created successfully", HttpStatus.CREATED.value());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<BookDTO>> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId());
        CustomResponse<BookDTO> response = new CustomResponse<>(bookDTO, "Book fetched successfully", HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<BookDTO>> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        Book updatedBook = bookRepository.save(book);
        BookDTO responseDTO = new BookDTO(updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getCategory().getId());
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

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<CustomResponse<List<BookDTO>>> getBooksByCategory(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    List<BookDTO> books = bookRepository.findByCategory(category)
                            .stream()
                            .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                            .collect(Collectors.toList());
                    CustomResponse<List<BookDTO>> response = new CustomResponse<>(books, "Books fetched successfully", HttpStatus.OK.value());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(new CustomResponse<>(null, "Category not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<CustomResponse<List<BookDTO>>> searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "categoryId", required = false) Long categoryId) {

        List<BookDTO> books;
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null) {
                if (title != null) {
                    books = bookRepository.findByCategoryAndTitleContainingIgnoreCase(category, title)
                            .stream()
                            .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                            .collect(Collectors.toList());
                } else if (author != null) {
                    books = bookRepository.findByCategoryAndAuthorContainingIgnoreCase(category, author)
                            .stream()
                            .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                            .collect(Collectors.toList());
                } else {
                    books = bookRepository.findByCategory(category)
                            .stream()
                            .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                            .collect(Collectors.toList());
                }
            } else {
                return new ResponseEntity<>(new CustomResponse<>(null, "Category not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } else {
            if (title != null) {
                books = bookRepository.findByTitleContainingIgnoreCase(title)
                        .stream()
                        .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                        .collect(Collectors.toList());
            } else if (author != null) {
                books = bookRepository.findByAuthorContainingIgnoreCase(author)
                        .stream()
                        .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                        .collect(Collectors.toList());
            } else {
                books = bookRepository.findAll()
                        .stream()
                        .map(book -> new BookDTO(book.getTitle(), book.getAuthor(), book.getCategory().getId()))
                        .collect(Collectors.toList());
            }
        }

        CustomResponse<List<BookDTO>> response = new CustomResponse<>(books, "Books fetched successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
