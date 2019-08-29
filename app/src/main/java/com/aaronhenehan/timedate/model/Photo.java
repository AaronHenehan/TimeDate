package com.aaronhenehan.timedate.model;

public class Photo {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }
}
