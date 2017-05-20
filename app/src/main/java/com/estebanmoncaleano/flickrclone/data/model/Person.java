package com.estebanmoncaleano.flickrclone.data.model;

public class Person {
    private String id;
    private String username;
    private String nsid;
    private String realname;
    private String location;
    private String description;
    private String photo_url;
    private String profile_url;
    private String mobile_url;
    private String first_date_taken;
    private int count;

    public Person() {
    }

    public Person(String id, String username, String nsid, String realname, String location, String description, String photo_url, String profile_url, String mobile_url, String first_date_taken, int count) {
        this.id = id;
        this.username = username;
        this.nsid = nsid;
        this.realname = realname;
        this.location = location;
        this.description = description;
        this.photo_url = photo_url;
        this.profile_url = profile_url;
        this.mobile_url = mobile_url;
        this.first_date_taken = first_date_taken;
        this.count = count;
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

    public String getNsid() {
        return nsid;
    }

    public void setNsid(String nsid) {
        this.nsid = nsid;
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

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getFirst_date_taken() {
        return first_date_taken;
    }

    public void setFirst_date_taken(String first_date_taken) {
        this.first_date_taken = first_date_taken;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
