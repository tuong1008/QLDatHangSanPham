package com.example.qldathangsanpham.Model;

public class SanPham {
    private int masp;
    private String tensp;
    private String xuatXu;
    private Double gia;

    public SanPham(int masp, String tensp, String xuatXu, Double gia) {
        this.masp = masp;
        this.tensp = tensp;
        this.xuatXu = xuatXu;
        this.gia = gia;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }
}