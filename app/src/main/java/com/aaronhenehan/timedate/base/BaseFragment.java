package com.aaronhenehan.timedate.base;

import androidx.fragment.app.Fragment;

import com.annimon.stream.Optional;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {
    protected CompositeDisposable compositeDisposable;

    @Override
    public void onDestroy() {
        Optional.ofNullable(compositeDisposable)
                .ifPresent(CompositeDisposable::dispose);
        super.onDestroy();
    }

    protected void addToDisposables(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(disposable);
    }
}
