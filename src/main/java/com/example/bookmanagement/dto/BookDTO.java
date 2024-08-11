package com.example.bookmanagement.dto;

public class BookDTO {
    private String title;
    private String author;
    private Long categoryId;

    public BookDTO(String title, String author, Long categoryId) {
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
