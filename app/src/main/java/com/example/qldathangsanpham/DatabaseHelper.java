package com.example.qldathangsanpham;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.qldathangsanpham.model.KhachHang;
import com.example.qldathangsanpham.model.SanPham;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    public static final String DB_NAME = "QuanLyDatHang"; // the name of our database
    public static final String TAG = DatabaseHelper.class.getName();
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
    public static final String CL_NGAY_DAT_HANG = "ngayDatHang";
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
            + CL_NGAY_DAT_HANG + " TEXT NOT NULL, "
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

    @SuppressLint("NewApi")
    public static int insertTaiKhoanNhanVien(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(CL_USERNAME, username);
        values.put(CL_PASSWORD, Utility.encryptPassword(password));
        return (int) db.insert(TB_TAI_KHOAN_NHAN_VIEN, null, values);
    }

    public static int insertHoSoNhanVien(SQLiteDatabase db, String hoTen, String avatar, String email, int taiKhoanNhanVienId) {
        ContentValues values = new ContentValues();
        values.put(CL_HO_TEN, hoTen);
        values.put(CL_AVATAR, avatar);
        values.put(CL_EMAIL, email);
        values.put(CL_TAI_KHOAN_NHAN_VIEN_ID, taiKhoanNhanVienId);
        return (int) db.insert(TB_HO_SO_NHAN_VIEN, null, values);
    }

    public static int insertHoSoKhachHang(SQLiteDatabase db, String hoTen, String diaChi, String SoDienThoai, String avatar) {
        ContentValues values = new ContentValues();
        values.put(CL_HO_TEN, hoTen);
        values.put(CL_DIA_CHI, diaChi);
        values.put(CL_SDT, SoDienThoai);
        values.put(CL_AVATAR, avatar);
        return (int) db.insert(TB_HO_SO_KHACH_HANG, null, values);
    }

    public static int insertDonDatHang(SQLiteDatabase db, String ngayDatHang, int taiKhoanNhanVienId, int hoSoKhachHangId) {
        ContentValues values = new ContentValues();
        values.put(CL_NGAY_DAT_HANG, ngayDatHang);
        values.put(CL_HO_SO_KHACH_HANG_ID, hoSoKhachHangId);
        values.put(CL_TAI_KHOAN_NHAN_VIEN_ID, taiKhoanNhanVienId);
        return (int) db.insert(TB_DON_DAT_HANG, null, values);
    }

    public static int insertSanPham(SQLiteDatabase db, String tenSanPham, float donGia, String xuatXu) {
        ContentValues values = new ContentValues();
        values.put(CL_TEN_SAN_PHAM, tenSanPham);
        values.put(CL_DON_GIA, donGia);
        values.put(CL_XUAT_XU, xuatXu);
        return (int) db.insert(TB_SAN_PHAM, null, values);
    }

    public static int insertCtDonDatHang(SQLiteDatabase db, int donDatHangId, int sanPhamId, int soLuong) {
        ContentValues values = new ContentValues();
        values.put(CL_DON_DAT_HANG_ID, donDatHangId);
        values.put(CL_SAN_PHAM_ID, sanPhamId);
        values.put(CL_SO_LUONG, soLuong);
        return (int) db.insert(TB_CT_DON_DAT_HANG, null, values);
    }

    public static int updateCtDonDatHang(SQLiteDatabase db, int donDatHangId, int sanPhamId, int soLuong) {
        ContentValues values = new ContentValues();
//        values.put(CL_DON_DAT_HANG_ID, donDatHangId);
//        values.put(CL_SAN_PHAM_ID, sanPhamId);
        values.put(CL_SO_LUONG, soLuong);
        return (int) db.update(TB_CT_DON_DAT_HANG, values, CL_DON_DAT_HANG_ID + " = ? AND " + CL_SAN_PHAM_ID + " = ?", new String[]{String.valueOf(donDatHangId), String.valueOf(sanPhamId)});
    }

    public static void updateCustomerFromDonDatHang(SQLiteDatabase db, int orderId, int customerId) {
        ContentValues values = new ContentValues();
        values.put(CL_HO_SO_KHACH_HANG_ID, customerId);
        db.update(TB_DON_DAT_HANG, values, CL_ID + " = ?", new String[]{String.valueOf(orderId)});
    }

    public static int deteleCtDonDatHang(SQLiteDatabase db, int donDatHangId, int sanPhamId) {
        return (int) db.delete(TB_CT_DON_DAT_HANG, CL_DON_DAT_HANG_ID + " = ? AND " + CL_SAN_PHAM_ID + " = ?", new String[]{String.valueOf(donDatHangId), String.valueOf(sanPhamId)});
    }

    public static int deleteDonDatHang(SQLiteDatabase db, int orderId) {
        db.delete(TB_CT_DON_DAT_HANG, CL_DON_DAT_HANG_ID + " = ? ", new String[]{String.valueOf(orderId)});
        return db.delete(TB_DON_DAT_HANG, CL_ID + " = ? ", new String[]{String.valueOf(orderId)});

    }

    @SuppressLint({"Range", "NewApi"})
    public static List<Map<String, String>> findAllOrder(Context context) {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT DDH." + CL_ID + ", HSKH." + CL_HO_TEN + ", DDH." + CL_NGAY_DAT_HANG + ", HSKH." + CL_ID + " AS customerId"
                + " FROM " + TB_DON_DAT_HANG + " AS DDH JOIN " + TB_HO_SO_KHACH_HANG
                + " AS HSKH ON DDH." + CL_HO_SO_KHACH_HANG_ID + " = HSKH." + CL_ID;

        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put(CL_ID, cursor.getString(cursor.getColumnIndex(CL_ID)));
                map.put(CL_HO_TEN, cursor.getString(cursor.getColumnIndex(CL_HO_TEN)));
                map.put(CL_NGAY_DAT_HANG, Utility.getDateFromDatetime(cursor.getString(cursor.getColumnIndex(CL_NGAY_DAT_HANG))));
                map.put("thoiGianDatHang", Utility.getTimeFromDatetime(cursor.getString(cursor.getColumnIndex(CL_NGAY_DAT_HANG))));
                map.put("customerId", cursor.getString(cursor.getColumnIndex("customerId")));
                list.add(map);
            }
            cursor.close();
        } catch (SQLiteException | ParseException e) {
            Toast toast = Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return list;
    }

    @SuppressLint("Range")
    public static Map<String, String> findCustomerById(Context context, int customerId) {
        Map<String, String> map = new HashMap<>();
        String query = "SELECT " + CL_HO_TEN + ", " + CL_SDT + ", " + CL_DIA_CHI
                + " FROM " + TB_HO_SO_KHACH_HANG + " WHERE " + CL_ID + " = " + customerId;

        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                map.put(CL_SDT, cursor.getString(cursor.getColumnIndex(CL_SDT)));
                map.put(CL_HO_TEN, cursor.getString(cursor.getColumnIndex(CL_HO_TEN)));
                map.put(CL_DIA_CHI, cursor.getString(cursor.getColumnIndex(CL_DIA_CHI)));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return map;
    }

    @SuppressLint("Range")
    public static List<Map<String, String>> findAllCustomer(Context context) {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT " + CL_ID + ", " + CL_HO_TEN + ", " + CL_SDT + ", " + CL_DIA_CHI
                + " FROM " + TB_HO_SO_KHACH_HANG;

        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put(CL_HO_TEN, cursor.getString(cursor.getColumnIndex(CL_HO_TEN)));
                map.put(CL_SDT, cursor.getString(cursor.getColumnIndex(CL_SDT)));
                map.put(CL_DIA_CHI, cursor.getString(cursor.getColumnIndex(CL_DIA_CHI)));
                map.put(CL_ID, cursor.getString(cursor.getColumnIndex(CL_ID)));
                list.add(map);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return list;
    }

    @SuppressLint("Range")
    public static List<Map<String, String>> findProductInOrder(Context context, int orderId) {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT SP." + CL_TEN_SAN_PHAM + ", SP." + CL_DON_GIA + ", CTDDH." + CL_SO_LUONG + ", SP." + CL_ID
                + " FROM " + TB_CT_DON_DAT_HANG + " AS CTDDH JOIN " + TB_SAN_PHAM
                + " AS SP ON CTDDH." + CL_DON_DAT_HANG_ID + " = " + orderId
                + " AND CTDDH." + CL_SAN_PHAM_ID + " = SP." + CL_ID;
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put(CL_TEN_SAN_PHAM, cursor.getString(cursor.getColumnIndex(CL_TEN_SAN_PHAM)));
                map.put(CL_DON_GIA, String.valueOf(cursor.getString(cursor.getColumnIndex(CL_DON_GIA))));
                map.put(CL_SO_LUONG, String.valueOf(cursor.getString(cursor.getColumnIndex(CL_SO_LUONG))));
                map.put(CL_ID, String.valueOf(cursor.getString(cursor.getColumnIndex(CL_ID))));
                list.add(map);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return list;
    }

    @SuppressLint("Range")
    public static List<Map<String, String>> findProductNotInOrder(Context context, int orderId) {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT " + CL_TEN_SAN_PHAM + ", " + CL_DON_GIA + ", " + CL_ID
                + " FROM " + TB_SAN_PHAM + " WHERE " + CL_ID + " NOT IN (SELECT " + CL_SAN_PHAM_ID + " FROM " + TB_CT_DON_DAT_HANG + " WHERE " + CL_DON_DAT_HANG_ID + " = " + orderId + ")";
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put(CL_TEN_SAN_PHAM, cursor.getString(cursor.getColumnIndex(CL_TEN_SAN_PHAM)));
                map.put(CL_DON_GIA, String.valueOf(cursor.getString(cursor.getColumnIndex(CL_DON_GIA))));
                map.put(CL_ID, String.valueOf(cursor.getString(cursor.getColumnIndex(CL_ID))));
                list.add(map);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, orderId + "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return list;
    }

    @SuppressLint("Range")
    public static List<Map<String, String>> getDataForOderChart(Context context) {
        List<Map<String, String>> list = new ArrayList<>();
        String query = "SELECT COUNT( DDH." + CL_ID + ") AS SLDDH ,KH." + CL_HO_TEN + " ,KH." + CL_ID
                + " FROM " + TB_DON_DAT_HANG + " AS DDH JOIN " + TB_HO_SO_KHACH_HANG + " AS KH ON DDH."
                + CL_HO_SO_KHACH_HANG_ID + " = KH." + CL_ID + " GROUP BY KH." + CL_ID;
//        String query = "SELECT COUNT(" + CL_ID + ") AS SLDDH ," + CL_HO_SO_KHACH_HANG_ID
//                + " FROM " + TB_DON_DAT_HANG + " ORDER BY " + CL_HO_SO_KHACH_HANG_ID;
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put(CL_ID, String.valueOf(cursor.getString(cursor.getColumnIndex(CL_ID))));
                map.put(CL_HO_TEN, cursor.getString(cursor.getColumnIndex(CL_HO_TEN)));
                map.put("SLDDH", String.valueOf(cursor.getString(cursor.getColumnIndex("SLDDH"))));
                list.add(map);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return list;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertData(SQLiteDatabase db) {
        insertTaiKhoanNhanVien(db, "nguyenmanhtuong@gmail.com", "nguyenmanhtuong");
        insertTaiKhoanNhanVien(db, "buiminhto@gmail.com", "buiminhto");
        insertTaiKhoanNhanVien(db, "ledinhtrieu@gmail.com", "ledinhtrieu");
        insertTaiKhoanNhanVien(db, "nguyentanthien@gmail.com", "nguyentanthien");
        insertTaiKhoanNhanVien(db, "nguyenthanhtu@gmail.com", "nguyenthanhtu");
        insertHoSoKhachHang(db, "Nguyễn Lê Tấn Tài", "Thủ Đức, TPHCM", "0987654321", "1");
        insertHoSoKhachHang(db, "Lê Trọng Đạt", "Thủ Đức, TPHCM", "8970777444", "1");
        insertHoSoKhachHang(db, "Võ Đặng Kế Định", "Thủ Đức, TPHCM", "0987912345", "1");
        insertHoSoKhachHang(db, "Cao Thành Lợi", "Thủ Đức, TPHCM", "1275849586", "1");
        insertHoSoKhachHang(db, "Bùi Tấn Sang", "Thủ Đức, TPHCM", "9057485769", "1");
        insertSanPham(db, "TV Samsung 55 inch", 20000000, "VN");
        insertSanPham(db, "Tủ Lạnh Panasonic 2 Ngăn", 10000000, "JP");
        insertSanPham(db, "TV Sony 40 inch", 15000000, "KR");
        insertSanPham(db, "Máy Giặt Sharp 10L", 7000000, "RU");
        insertSanPham(db, "Laptop Dell Inspiron 7559", 14000000, "US");
        insertDonDatHang(db, Utility.getCurrentDateTime(), 1, 1);
        insertDonDatHang(db, Utility.getCurrentDateTime(), 2, 2);
        insertDonDatHang(db, Utility.getCurrentDateTime(), 3, 3);
        insertDonDatHang(db, Utility.getCurrentDateTime(), 4, 4);
        insertCtDonDatHang(db, 1, 1, 10);
        insertCtDonDatHang(db, 1, 2, 10);
        insertCtDonDatHang(db, 1, 3, 10);
        insertCtDonDatHang(db, 1, 4, 10);
        insertCtDonDatHang(db, 2, 1, 10);
        insertCtDonDatHang(db, 2, 2, 10);
        insertCtDonDatHang(db, 2, 3, 10);
        insertCtDonDatHang(db, 2, 4, 10);
        insertCtDonDatHang(db, 3, 1, 10);
        insertCtDonDatHang(db, 3, 2, 10);
        insertCtDonDatHang(db, 3, 3, 10);
        insertCtDonDatHang(db, 3, 4, 10);
        insertCtDonDatHang(db, 4, 1, 10);
        insertCtDonDatHang(db, 4, 2, 10);
        insertCtDonDatHang(db, 4, 3, 10);
        insertCtDonDatHang(db, 4, 4, 10);
    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL(QUERY_CREATE_TB_TAI_KHOAN_NHAN_VIEN);
            db.execSQL(QUERY_CREATE_TB_HO_SO_KHACH_HANG);
            db.execSQL(QUERY_CREATE_TB_HO_SO_NHAN_VIEN);
            db.execSQL(QUERY_CREATE_TB_SAN_PHAM);
            db.execSQL(QUERY_CREATE_TB_DON_DAT_HANG);
            db.execSQL(QUERY_CREATE_TB_CT_DON_DAT_HANG);
            insertData(db);
        }
    }

    public int nextAutoIncrement(String table) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT MAX(_id) AS max_id FROM " + table;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return id + 1;
    }


    public String insertCustomer(String tenKH, String diaChi,
                                 String soDT, String avatarPath) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues customerValues = new ContentValues();
            customerValues.put(CL_HO_TEN, tenKH);
            customerValues.put(CL_DIA_CHI, diaChi);
            customerValues.put(CL_SDT, soDT);
            customerValues.put(CL_AVATAR, avatarPath);

            db.insertOrThrow(TB_HO_SO_KHACH_HANG, null, customerValues);
            db.setTransactionSuccessful();

            return null;
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            db.endTransaction();
        }
    }

    public String updateCustomer(String tenKH, String diaChi,
                                 String soDT, String maKH) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues customerValues = new ContentValues();
            customerValues.put(CL_HO_TEN, tenKH);
            customerValues.put(CL_DIA_CHI, diaChi);
            customerValues.put(CL_SDT, soDT);

            db.update(TB_HO_SO_KHACH_HANG, customerValues, "_id = ?", new String[]{maKH});
            db.setTransactionSuccessful();

            return null;
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            db.endTransaction();
        }


    }

    public String deleteCustomer(String maKH) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TB_HO_SO_KHACH_HANG, "_id = ?", new String[]{maKH});
            db.setTransactionSuccessful();

            return null;
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            db.endTransaction();
        }
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

    public List<KhachHang> getAllCustomers() {
        SQLiteDatabase db = getReadableDatabase();
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
            Log.d("QueryDB", "Error while trying to get list customers from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    public TreeMap<String, Integer> getThongKeTenKH() {
        SQLiteDatabase db = getReadableDatabase();
        TreeMap<String, Integer> list = new TreeMap<String, Integer>();

        Cursor cursor = db.rawQuery("SELECT substr(hoTen,1,1) as ChuCaiDau, count(*) as SoLuong FROM HoSoKhachHang\n" +
                "GROUP by ChuCaiDau", null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    list.put(cursor.getString(cursor.getColumnIndexOrThrow("ChuCaiDau")),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("SoLuong"))));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("QueryDB", "Error while trying to count name of customers from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }
}