package com.example.qldathangsanpham.ui.login;

public class ChangePasswordResult {

    private String sussess;
    private Integer error;

    public ChangePasswordResult(String sussess) {
        this.sussess = sussess;
    }

    public ChangePasswordResult(Integer error) {
        this.error = error;
    }

    public String getSussess() {
        return sussess;
    }

    public Integer getError() {
        return error;
    }
}
