package com.example.qldathangsanpham.ui.login;

import androidx.annotation.Nullable;

public class RegisterFromState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer hovatenError;

    @Nullable
    private Integer avatarError;

    public RegisterFromState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer hovatenError, @Nullable Integer avatarError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.hovatenError = hovatenError;
        this.avatarError = avatarError;
        this.isDataValid = false;
    }

    public RegisterFromState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.hovatenError = null;
        this.avatarError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getHovatenError() {
        return hovatenError;
    }

    @Nullable
    public Integer getAvatarError() {
        return avatarError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

    private boolean isDataValid = false;


}
