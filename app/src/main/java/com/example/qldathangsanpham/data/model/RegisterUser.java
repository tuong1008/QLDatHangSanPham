package com.example.qldathangsanpham.data.model;

public class RegisterUser {
    private String email;
    private int id;

    public RegisterUser(String email, int id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }


    public int getId() {
        return id;
    }

}
