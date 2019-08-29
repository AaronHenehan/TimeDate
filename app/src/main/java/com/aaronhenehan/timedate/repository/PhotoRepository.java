package com.aaronhenehan.timedate.repository;

import androidx.lifecycle.LiveData;

import com.aaronhenehan.timedate.api.FlickrService;
import com.aaronhenehan.timedate.model.Photo;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PhotoRepository {
    private static PhotoRepository photoRepository;
    private FlickrService flickrService;

    public static PhotoRepository getInstance() {
        if (photoRepository == null) {
            photoRepository = new PhotoRepository();
        }
        return photoRepository;
    }

    private PhotoRepository() {
        flickrService = new FlickrService();
    }

    public LiveData<List<Photo>> getPhotoLiveData() {
        return flickrService.getPhotoLiveData();
    }

    public void searchPhotosByTags(String tags) {
        flickrService.getPhotosByTags(tags);
    }

    public void searchPhotosByUploadDate(long date) {
        flickrService.getPhotosByUploadDate(date, date + TimeUnit.DAYS.toMillis(1));
    }
}
