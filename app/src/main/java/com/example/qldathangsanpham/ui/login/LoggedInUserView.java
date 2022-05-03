package com.example.qldathangsanpham.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    private String id;


    public String getId() {
        return id;
    }


    public LoggedInUserView(String displayName, String id) {
        this.displayName = displayName;
        this.id = id;
    }

    String getDisplayName() {
        return displayName;
    }
}