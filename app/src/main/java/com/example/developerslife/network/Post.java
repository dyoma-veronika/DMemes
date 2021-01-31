package com.example.developerslife.network;

public class Post {

    private int id;
    private String gifURL;
    private String description;

    public Post(int id, String gifURL, String description) {
        this.id = id;
        this.gifURL = gifURL;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getGifURL() {
        return gifURL;
    }

    public String getDescription() {
        return description;
    }
}
