package com.example.qldathangsanpham.ui.customer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qldathangsanpham.R;

public class AngDoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "QLDatHangSanPham"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

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
//        if (oldVersion <2){
//            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
//        }
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
}
