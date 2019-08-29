package com.aaronhenehan.timedate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrPhotoObject {
    @SerializedName("photo")
    private List<Photo> photos;
    private int page;
    private int pages;
    private int perpage;
    private String total;

    public List<Photo> getPhotos() {
        return photos;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public String getTotal() {
        return total;
    }
}
