package com.example.qldathangsanpham;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.qldathangsanpham.model.KhachHang;
import com.example.qldathangsanpham.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    public static final String TAG = DatabaseHelper.class.getName();
    public static final String DB_NAME = "QUAN_LY_DAT_HANG"; // the name of our database
    public static final int DB_VERSION = 1; // the version of the database
    public static final String TB_HO_SO_NHAN_VIEN = "HoSoNhanVien";
    public static final String TB_TAI_KHOAN_NHAN_VIEN = "TaiKhoanNhanVien";
    public static final String TB_DON_DAT_HANG = "DonDatHang";
    public static final String TB_HO_SO_KHACH_HANG = "HoSoKhachHang";
    public static final String TB_SAN_PHAM = "SanPham";
    public static final String TB_CT_DON_DAT_HANG = "CtDonDatHang";
    public static final String CL_ID = "_id";
    public static final String CL_AVATAR = "avatar";
    public static final String CL_EMAIL = "email";
    public static final String CL_HO_TEN = "hoTen";
    public static final String CL_USERNAME = "username";
    public static final String CL_PASSWORD = "password";
    public static final String Cl_NGAY_DAT_HANG = "ngayDatHang";
    public static final String CL_DIA_CHI = "diaChi";
    public static final String CL_SDT = "sdt";
    public static final String CL_SO_LUONG = "soLuong";
    public static final String CL_TEN_SAN_PHAM = "tenSanPham";
    public static final String CL_DON_GIA = "donGia";
    public static final String CL_XUAT_XU = "xuatXu";
    public static final String CL_TAI_KHOAN_NHAN_VIEN_ID = "taiKhoanNhanVienId";
    public static final String CL_HO_SO_KHACH_HANG_ID = "hoSoKhachHangId";
    public static final String CL_SAN_PHAM_ID = "sanPhamId";
    public static final String CL_DON_DAT_HANG_ID = "donDatHangId";


    //tai khoan nhan vien
    public static final String QUERY_CREATE_TB_TAI_KHOAN_NHAN_VIEN = "CREATE TABLE " + TB_TAI_KHOAN_NHAN_VIEN + " ("
            + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CL_USERNAME + " TEXT NOT NULL UNIQUE, "
            + CL_PASSWORD + " TEXT NOT NULL);";

    //ho so nhan vien
    public static final String QUERY_CREATE_TB_HO_SO_NHAN_VIEN = "CREATE TABLE " + TB_HO_SO_NHAN_VIEN + " ("
            + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CL_HO_TEN + " TEXT NOT NULL, "
            + CL_EMAIL + " TEXT NOT NULL UNIQUE, "
            + CL_AVATAR + " TEXT, "
            + CL_TAI_KHOAN_NHAN_VIEN_ID + " INTEGER, "
            + "FOREIGN KEY(" + CL_TAI_KHOAN_NHAN_VIEN_ID + ") REFERENCES " + TB_TAI_KHOAN_NHAN_VIEN + "(" + CL_ID + "));";

    //ho so khach hang
    public static final String QUERY_CREATE_TB_HO_SO_KHACH_HANG = "CREATE TABLE " + TB_HO_SO_KHACH_HANG + " ("
            + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CL_HO_TEN + " TEXT NOT NULL, "
            + CL_SDT + " TEXT NOT NULL UNIQUE, "
            + CL_AVATAR + " TEXT, "
            + CL_DIA_CHI + " TEXT);";

    //san pham
    public static final String QUERY_CREATE_TB_SAN_PHAM = "CREATE TABLE " + TB_SAN_PHAM + " ("
            + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CL_TEN_SAN_PHAM + " TEXT NOT NULL, "
            + CL_DON_GIA + " NUMERIC NOT NULL, "
            + CL_XUAT_XU + " TEXT);";

    //don dat hang
    public static final String QUERY_CREATE_TB_DON_DAT_HANG = "CREATE TABLE " + TB_DON_DAT_HANG + " ("
            + CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Cl_NGAY_DAT_HANG + " TEXT NOT NULL UNIQUE, "
            + CL_TAI_KHOAN_NHAN_VIEN_ID + " INTEGER NOT NULL, "
            + CL_HO_SO_KHACH_HANG_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + CL_HO_SO_KHACH_HANG_ID + ") REFERENCES " + TB_HO_SO_KHACH_HANG + "(" + CL_ID + "), "
            + "FOREIGN KEY(" + CL_TAI_KHOAN_NHAN_VIEN_ID + ") REFERENCES " + TB_TAI_KHOAN_NHAN_VIEN + "(" + CL_ID + "));";

    //chi tiet don dat hang
    public static final String QUERY_CREATE_TB_CT_DON_DAT_HANG = "CREATE TABLE " + TB_CT_DON_DAT_HANG + " ("
            + CL_SO_LUONG + " INTEGER NOT NULL, "
            + CL_SAN_PHAM_ID + " INTEGER NOT NULL, "
            + CL_DON_DAT_HANG_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + CL_SAN_PHAM_ID + ") REFERENCES " + TB_SAN_PHAM + "(" + CL_ID + "), "
            + "FOREIGN KEY(" + CL_DON_DAT_HANG_ID + ") REFERENCES " + TB_DON_DAT_HANG + "(" + CL_ID + "), "
            + "PRIMARY KEY(" + CL_SAN_PHAM_ID + ", " + CL_DON_DAT_HANG_ID + "));";


    public DatabaseHelper(Context context) {
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

    public void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL(QUERY_CREATE_TB_TAI_KHOAN_NHAN_VIEN);
            db.execSQL(QUERY_CREATE_TB_HO_SO_KHACH_HANG);
            db.execSQL(QUERY_CREATE_TB_HO_SO_NHAN_VIEN);
            db.execSQL(QUERY_CREATE_TB_SAN_PHAM);
            db.execSQL(QUERY_CREATE_TB_DON_DAT_HANG);
            db.execSQL(QUERY_CREATE_TB_CT_DON_DAT_HANG);

        }
    }

    public static int nextAutoIncrement(SQLiteDatabase db, String table) {
        String query = "SELECT MAX(_id) AS max_id FROM " + table;
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return id + 1;
    }

    public List<KhachHang> getAllCustomers(SQLiteDatabase db) {
        List<KhachHang> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_HO_SO_KHACH_HANG, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    KhachHang cus = new KhachHang();
                    cus.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(CL_ID))));
                    cus.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(CL_HO_TEN)));
                    cus.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow(CL_DIA_CHI)));
                    cus.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(CL_AVATAR)));
                    cus.setSdt(cursor.getString(cursor.getColumnIndexOrThrow(CL_SDT)));

                    list.add(cus);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get list customers from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    public static void insertCustomer(SQLiteDatabase db, String tenKH, String diaChi,
                                      String soDT, String avatarPath) {
        ContentValues customerValues = new ContentValues();
        customerValues.put(CL_HO_TEN, tenKH);
        customerValues.put(CL_DIA_CHI, diaChi);
        customerValues.put(CL_SDT, soDT);
        customerValues.put(CL_AVATAR, avatarPath);
        db.insert(TB_HO_SO_KHACH_HANG, null, customerValues);
    }

    public static void updateCustomer(SQLiteDatabase db, String tenKH, String diaChi,
                                      String soDT, String maKH) {
        ContentValues customerValues = new ContentValues();
        customerValues.put(CL_HO_TEN, tenKH);
        customerValues.put(CL_DIA_CHI, diaChi);
        customerValues.put(CL_SDT, soDT);
        db.update(TB_HO_SO_KHACH_HANG, customerValues, "_id = ?", new String[]{maKH});
    }

    public static void deleteCustomer(SQLiteDatabase db, String maKH) {
        db.delete(TB_HO_SO_KHACH_HANG, "_id = ?", new String[]{maKH});
    }

    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_SAN_PHAM, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    SanPham sp = new SanPham();
                    sp.setMasp((int) Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CL_ID))));
                    sp.setTensp(cursor.getString(cursor.getColumnIndexOrThrow(CL_TEN_SAN_PHAM)));
                    sp.setGia(Double.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(CL_DON_GIA))));
                    sp.setXuatXu(cursor.getString(cursor.getColumnIndexOrThrow(CL_XUAT_XU)));

                    list.add(sp);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get list sanpham");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    public boolean addSanPham(SanPham sp) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(CL_TEN_SAN_PHAM, sp.getTensp());
            values.put(CL_XUAT_XU, sp.getXuatXu());
            values.put(CL_DON_GIA, sp.getGia());

            db.insertOrThrow(TB_SAN_PHAM, null, values);
            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add san pham");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean updateSanPham(SanPham sp) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(CL_TEN_SAN_PHAM, sp.getTensp());
            values.put(CL_XUAT_XU, sp.getXuatXu());
            values.put(CL_DON_GIA, sp.getGia());

            db.update(TB_SAN_PHAM, values, "_id = ?", new String[]{String.valueOf(sp.getMasp())});
            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update sanpham");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean deleteSanPham(long id) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(TB_SAN_PHAM, "_id=?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete san pham");
            return false;
        } finally {
            db.endTransaction();
        }
    }
}