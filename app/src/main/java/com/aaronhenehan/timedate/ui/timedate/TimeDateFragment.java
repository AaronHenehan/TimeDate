package com.aaronhenehan.timedate.ui.timedate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.aaronhenehan.timedate.base.BaseFragment;
import com.aaronhenehan.timedate.databinding.FragmentTimedateBinding;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeDateFragment extends BaseFragment {
    private static final String TAG = "TimeDateFragment";

    private FragmentTimedateBinding binding;
    private TimeDateViewModel timeDateViewModel;
    private long selectedDate;
    private Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTimedateBinding.inflate(inflater, container, false);

        timeDateViewModel = ViewModelProviders.of(this).get(TimeDateViewModel.class);
        CalendarView calendarView = binding.timedateCalendar;
        calendarView.setMaxDate(calendarView.getDate());
        // Set the initial time at 00:00 today
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0, 0);
        selectedDate = calendar.getTimeInMillis();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedDate = calendar.getTimeInMillis();
        });

        addToDisposables(RxView.clicks(binding.timedateSearchTags)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .map(__ -> binding.timedateTagSearch.getText().toString())
                .filter(text -> text != null && !text.isEmpty())
                .subscribe(text -> {
                    Log.d(TAG, "Search Tags Tapped: " + selectedDate);
                    timeDateViewModel.searchForPhotosByTag(text);
                    onSearchClicked(text);
                }));

        addToDisposables(RxView.clicks(binding.timedateSearchDate)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(__ -> {
                    Log.d(TAG, "Search Date Tapped: " + selectedDate);
                    timeDateViewModel.searchForPhotosByUploadDate(selectedDate);
                    onSearchClicked(timeDateViewModel.convertUnixTimeToDate(selectedDate));
                }));

        return binding.getRoot();
    }

    private void onSearchClicked(String searchTerm) {
        TimeDateFragmentDirections.ActionTimedateToResults action
                = TimeDateFragmentDirections.actionTimedateToResults(searchTerm);
        NavHostFragment.findNavController(this)
                .navigate(action);
    }
}
