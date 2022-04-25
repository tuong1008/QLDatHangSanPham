package com.example.qldathangsanpham.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qldathangsanpham.data.RegisterDataSource;
import com.example.qldathangsanpham.data.RegisterRepository;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {
    private Context application;

    public RegisterViewModelFactory(Context application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(RegisterRepository.getInstance(new RegisterDataSource(application)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
