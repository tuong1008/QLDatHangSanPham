package com.example.qldathangsanpham.model;

public class KhachHang {
    private int _id;
    private String hoTen;
    private String sdt;
    private String avatar;
    private String diaChi;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int get_id() {
        return _id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDiaChi() {
        return diaChi;
    }
}
