package com.estebanmoncaleano.flickrclone.data.model;

public class People {
    private String id;
    private String username;
    private String realname;
    private String location;
    private String description;
    private String photo_url;

    public People() {
    }

    public People(String id, String username, String realname, String location, String description, String photo_url) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.location = location;
        this.description = description;
        this.photo_url = photo_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
