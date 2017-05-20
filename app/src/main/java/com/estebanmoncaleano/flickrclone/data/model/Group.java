package com.estebanmoncaleano.flickrclone.data.model;

public class Group {
    private int id;
    private String name;
    private String description;
    private String rules;
    private int members;
    private int topics;

    public Group() {
    }

    public Group(int id, String name, String description, String rules, int members, int topics) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.members = members;
        this.topics = topics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }
}
