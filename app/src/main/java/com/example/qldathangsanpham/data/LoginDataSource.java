package com.example.qldathangsanpham.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qldathangsanpham.AngDoDatabaseHelper;
import com.example.qldathangsanpham.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private Context mcontext;
    private SQLiteDatabase mDatabase;

    public LoginDataSource(Context mcontext) {
        this.mcontext = mcontext;
        mDatabase=new AngDoDatabaseHelper(mcontext).getWritableDatabase();
    }
    public Result<LoggedInUser> login(String username, String password) {
        try {
            // TODO: handle loggedInUser authentication
            mDatabase=new AngDoDatabaseHelper(mcontext).getWritableDatabase();
            Cursor  cursor=mDatabase.query("TAIKHOAN",null,"USERNAME=?",new String[]{username},null,null,null);
            if(cursor.moveToFirst())
            {
                String id=cursor.getString(0);
                String userName=cursor.getString(1);
                String passWord=cursor.getString(2);
                if(userName.equals(username)&&passWord.equals(password))
                {
                    LoggedInUser loggedInUser=new LoggedInUser(id,userName);
                    return new Result.Success<>(loggedInUser);
                }
                else{
                    return new Result.Error(new RuntimeException("Username or password invalid"));
                }
            }
            else{
                return new Result.Error(new RuntimeException("Username or password not found"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
        finally {
            mDatabase.close();
        }
    }

    public Boolean changePassword(String username,String password,String newPassword){
        try{
            mDatabase=new AngDoDatabaseHelper(mcontext).getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("password",newPassword);
         if(mDatabase.update("TaiKhoan",values,"username=? and password=?",new String[]{username,password})>0){
             return true;
         }
         else{
             return false;
         }
        }finally {
            mDatabase.close();
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }
}