package com.example.project1.Model;

public class Category {
    String id, category,uid;
    long time;

    public Category() {
    }

    public Category(String id, String category, String uid, long time) {
        this.id = id;
        this.category = category;
        this.uid = uid;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
