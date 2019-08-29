package com.aaronhenehan.timedate.ui.photodisplay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.aaronhenehan.timedate.model.Photo;
import com.aaronhenehan.timedate.repository.PhotoRepository;

import java.util.List;

public class PhotoResultsViewModel extends ViewModel {
    private LiveData<List<Photo>> photoLiveData;

    public PhotoResultsViewModel() {
        photoLiveData = PhotoRepository.getInstance().getPhotoLiveData();
    }

    public LiveData<List<Photo>> getPhotoLiveData() {
        return photoLiveData;
    }
}
