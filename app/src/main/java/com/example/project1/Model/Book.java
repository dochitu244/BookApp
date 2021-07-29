package com.example.project1.Model;

public class Book {
    private int resId;
    private String title;
    private String author;

    public Book() {
    }

    public Book(int resId) {
        this.resId = resId;
    }

    public Book(int resId, String title, String author) {
        this.resId = resId;
        this.title = title;
        this.author = author;
    }

    public Book(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
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
}
