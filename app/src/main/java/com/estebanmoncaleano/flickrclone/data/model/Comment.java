package com.estebanmoncaleano.flickrclone.data.model;

public class Comment {
    private int id;
    private int photo_id;
    private String author;
    private String author_name;
    private String message;

    public Comment() {
    }

    public Comment(int id, int photo_id, String author, String author_name, String message) {
        this.id = id;
        this.photo_id = photo_id;
        this.author = author;
        this.author_name = author_name;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
