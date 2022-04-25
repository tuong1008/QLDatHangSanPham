package com.example.qldathangsanpham;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AngDoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "QLDatHangSanPham"; // the name of our database
    private static final int DB_VERSION =5; // the version of the database

    public AngDoDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE KHACHHANG (maKH INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "tenKH TEXT, "
                    + "diaChi TEXT, "
                    + "soDT TEXT, "
                    + "avatar INTEGER);");
            insertDrink(db, "Latte", "Espresso and steamed milk", "0334139916", R.drawable.duck);
        }
       if (oldVersion <2){
          // db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
           db.execSQL("CREATE TABLE TAIKHOAN (MATK INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "USERNAME TEXT, "
                   + "PASSWORD TEXT);");
           db.execSQL("CREATE TABLE HOSONHANVIEN (MAHOSO INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "AVATAR BLOB, "
                   + "HOTEN TEXT,"
                   + "TAIKHOANID INTEGER,"
                   + "  FOREIGN KEY(TAIKHOANID) REFERENCES TAIKHOAN(MATK));");
        insertTaiKhoan(db,"minhto@gmail.com","minhto123");
        }
        if (oldVersion <3){
            // db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
            insertTaiKhoan(db,"minhto@gmail.com","minhto123");
        }

        if (oldVersion <4){
            // db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");

            insertTaiKhoan(db,"minhtu@gmail.com","minhto123");
        }
        if (oldVersion <5){
           //  db.execSQL("MODIFY TABLE HOSONHANVIEN ALTER COLUMN FAVORITE NUMERIC;");


        }

    }

    private static void insertDrink(SQLiteDatabase db, String tenKH, String diaChi,
                                    String soDT, int resourceId) {
        ContentValues customerValues = new ContentValues();
        customerValues.put("tenKH", tenKH);
        customerValues.put("diaChi", diaChi);
        customerValues.put("soDT", soDT);
        customerValues.put("avatar", resourceId);
        db.insert("KHACHHANG", null, customerValues);
    }
    private static void insertTaiKhoan(SQLiteDatabase db,String username,String password)
    {
    ContentValues taiKhoanValues=new ContentValues();
    taiKhoanValues.put("USERNAME",username);
    taiKhoanValues.put("PASSWORD",password);
    db.insert("TAIKHOAN",null,taiKhoanValues);
    }
}
