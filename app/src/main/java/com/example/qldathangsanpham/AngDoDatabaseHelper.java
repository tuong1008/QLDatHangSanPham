package com.example.qldathangsanpham;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                    + "avatar TEXT);");
        }
//        if (oldVersion <2){
//            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
//        }
    }

    public static int nextAutoIncrement(SQLiteDatabase db, String table, String primaryKey){
        String query = "SELECT MAX("+primaryKey+") AS max_id FROM "+ table;
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while(cursor.moveToNext());
        }
        return id+1;
    }

    public static void insertCustomer(SQLiteDatabase db, String tenKH, String diaChi,
                                    String soDT, String avatarPath) {
        ContentValues customerValues = new ContentValues();
        customerValues.put("tenKH", tenKH);
        customerValues.put("diaChi", diaChi);
        customerValues.put("soDT", soDT);
        customerValues.put("avatar", avatarPath);
        db.insert("KHACHHANG", null, customerValues);
    }

    public static void updateCustomer(SQLiteDatabase db, String tenKH, String diaChi,
                                      String soDT, String maKH) {
        ContentValues customerValues = new ContentValues();
        customerValues.put("tenKH", tenKH);
        customerValues.put("diaChi", diaChi);
        customerValues.put("soDT", soDT);
        db.update("KHACHHANG", customerValues, "maKH = ?", new String[] {maKH});
    }

    public static void deleteCustomer(SQLiteDatabase db, String maKH) {
        db.delete("KHACHHANG", "maKH = ?", new String[] {maKH});
    }
}
