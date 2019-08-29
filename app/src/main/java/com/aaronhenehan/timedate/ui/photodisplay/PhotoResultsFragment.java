package com.aaronhenehan.timedate.ui.photodisplay;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aaronhenehan.timedate.R;
import com.aaronhenehan.timedate.base.BaseFragment;
import com.aaronhenehan.timedate.databinding.FragmentPhotoResultsBinding;
import com.annimon.stream.Optional;

public class PhotoResultsFragment extends BaseFragment {

    private FragmentPhotoResultsBinding binding;
    private PhotoResultsViewModel photoResultsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotoResultsBinding.inflate(inflater, container, false);

        Optional.ofNullable(getArguments())
                .ifPresent(args -> {
                    String searchTerm = PhotoResultsFragmentArgs.fromBundle(getArguments()).getSearchTerm();
                    binding.photoResultsTitle.setText(getString(R.string.search_photos_by, searchTerm));
                });

        RecyclerView photoRecycler = binding.photoResultsRecycler;

        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                ? 2
                : 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);

        photoRecycler.setLayoutManager(gridLayoutManager);
        photoRecycler.setHasFixedSize(true);

        PhotoAdapter photoAdapter = new PhotoAdapter();
        photoRecycler.setAdapter(photoAdapter);

        photoResultsViewModel = ViewModelProviders.of(this).get(PhotoResultsViewModel.class);
        photoResultsViewModel.getPhotoLiveData().observe(this, photos -> {
            photoAdapter.setPhotos(photos);
        });

        return binding.getRoot();
    }
}
