package com.example.qldathangsanpham.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.qldathangsanpham.data.EmployeeDataSoure;
import com.example.qldathangsanpham.data.EmployeeRepository;

public class EmployeeViewModelFactory implements ViewModelProvider.Factory {
    private Context application;

    public EmployeeViewModelFactory(Context application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EmployeeViewModel.class)) {
            return (T) new EmployeeViewModel(EmployeeRepository.getInstance(new EmployeeDataSoure(application)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
