package com.example.qldathangsanpham.ui.login;

import androidx.annotation.Nullable;

public class ChangePasswordFromState {
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer newPasswordError;
    private boolean isDataValid;

    public ChangePasswordFromState(Integer passwordError, Integer newPasswordError) {
        this.passwordError = passwordError;
        this.newPasswordError = newPasswordError;
        isDataValid = false;
    }

    public ChangePasswordFromState(boolean isDataValid) {
        this.isDataValid = isDataValid;
        this.passwordError = null;
        this.newPasswordError = null;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getNewPasswordError() {
        return newPasswordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
