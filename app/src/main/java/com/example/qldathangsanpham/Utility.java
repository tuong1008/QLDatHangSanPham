package com.example.qldathangsanpham;

public class Utility {
    public static String showGia(Double gia) {
        return String.format("Giá: %,.0fđ", gia);
    }
}