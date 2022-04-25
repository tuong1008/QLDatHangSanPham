package com.example.qldathangsanpham.data.model;

public class EmployeeModel {
    String id;

    private String hoVaTen;
    private byte[] image;
    String maTaiKhoan;

    public EmployeeModel() {

    }

    public EmployeeModel(String id, String hoVaTen, byte[] image, String maTaiKhoan) {
        this.id = id;

        this.hoVaTen = hoVaTen;
        this.image = image;
        this.maTaiKhoan = maTaiKhoan;
    }


    public String getHoVaTen() {
        return hoVaTen;
    }

    public byte[] getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }
}
