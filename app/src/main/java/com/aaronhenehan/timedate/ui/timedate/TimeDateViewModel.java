package com.aaronhenehan.timedate.ui.timedate;

import androidx.lifecycle.ViewModel;

import com.aaronhenehan.timedate.repository.PhotoRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDateViewModel extends ViewModel {
    private SimpleDateFormat simpleDateFormat
            = new SimpleDateFormat("yyyy-MM-dd");

    public String convertUnixTimeToDate(long unixTime) {
        Date date  = new Date(unixTime);
        return simpleDateFormat.format(date);
    }

    public void searchForPhotosByTag(String tag) {
        PhotoRepository.getInstance().searchPhotosByTags(tag);
    }

    public void searchForPhotosByUploadDate(long date) {
        PhotoRepository.getInstance().searchPhotosByUploadDate(date);
    }
}
