package com.example.qldathangsanpham.ui.login;

import androidx.annotation.Nullable;

import com.example.qldathangsanpham.data.model.RegisterUser;

public class RegisterResult {
    @Nullable
    private RegisterUser success;
    @Nullable
    private  Integer error;

    public RegisterResult(@Nullable RegisterUser success) {
        this.success = success;
    }

    public RegisterResult(@Nullable Integer error) {
        this.error = error;
    }

    public RegisterUser getSuccess() {
        return success;
    }


    @Nullable
    public Integer getError() {
        return error;
    }
}
