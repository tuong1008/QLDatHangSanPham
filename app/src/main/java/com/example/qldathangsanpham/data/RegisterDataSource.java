package com.example.qldathangsanpham.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.qldathangsanpham.AngDoDatabaseHelper;
import com.example.qldathangsanpham.data.model.LoggedInUser;
import com.example.qldathangsanpham.data.model.RegisterUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {
    private Context mcontext;
    private SQLiteDatabase mDatabase;

    public RegisterDataSource(Context mcontext) {
        this.mcontext=mcontext;
        mDatabase=new AngDoDatabaseHelper(mcontext).getWritableDatabase();
    }

    public Result<RegisterUser> register(String username, String password,byte [] avatar,String hoVaTen) {
        mDatabase=new AngDoDatabaseHelper(mcontext).getWritableDatabase();
        try {
            // TODO: handle loggedInUser authentication
            ContentValues taiKhoanValue=new ContentValues();
            taiKhoanValue.put("username",username);
            taiKhoanValue.put("password",password);
            mDatabase.insert("TAIKHOAN",null,taiKhoanValue);
            int id;
            Cursor cursor=mDatabase.query("TAIKHOAN",null,"USERNAME=?",new String[]{username},null,null,null);
            if(cursor.moveToFirst())
            {
                ContentValues nhanVienValue=new ContentValues();
                nhanVienValue.put("HOTEN",hoVaTen);
                nhanVienValue.put("AVATAR",avatar);
                id=cursor.getInt(0);
                nhanVienValue.put("TAIKHOANID",id);
                mDatabase.insert("HOSONHANVIEN",null,nhanVienValue);
                Cursor cursorNhanVien=mDatabase.query("HOSONHANVIEN",null,"TAIKHOANID=?",new String[]{String.valueOf(id)},null,null,null);
                if(cursorNhanVien.moveToFirst())
                {
                    RegisterUser registerUser=new RegisterUser(cursorNhanVien.getString(2),cursorNhanVien.getInt(0));
                    return new Result.Success(registerUser);
                }
                cursorNhanVien.close();
            }
            cursor.close();
            mDatabase.close();

        return new Result.Error(new RuntimeException("Register failed"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}