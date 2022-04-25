package com.example.qldathangsanpham.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int masp;
    private String tensp;
    private String xuatXu;
    private Double gia;
    private String img;

    public SanPham() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    @Override
    public String toString() {
        return "SanPham{" +
                "masp=" + masp +
                ", tensp='" + tensp + '\'' +
                ", xuatXu='" + xuatXu + '\'' +
                ", gia=" + gia +
                '}';
    }
}