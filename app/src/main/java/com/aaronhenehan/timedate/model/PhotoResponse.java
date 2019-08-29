package com.aaronhenehan.timedate.model;

import com.google.gson.annotations.SerializedName;

public class PhotoResponse {
    @SerializedName("photos")
    private FlickrPhotoObject flickrPhotoObject;

    public FlickrPhotoObject getFlickrPhotoObject() {
        return flickrPhotoObject;
    }
}
