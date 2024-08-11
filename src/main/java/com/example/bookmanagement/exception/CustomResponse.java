package com.example.bookmanagement.exception;

public class CustomResponse<T> {

    private T data;
    private String message;
    private int status;

    public CustomResponse(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    // Getters ve Setters
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
