package com.example.qldathangsanpham.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.lifecycle.LiveData;

import com.example.qldathangsanpham.AngDoDatabaseHelper;
import com.example.qldathangsanpham.data.model.EmployeeModel;

import javax.sql.DataSource;

public class EmployeeDataSoure {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    public EmployeeDataSoure(Context context) {
        this.context=context;
        this.sqLiteOpenHelper = new AngDoDatabaseHelper(this.context);
        this.sqLiteDatabase=this.sqLiteOpenHelper.getReadableDatabase();

    }

    public EmployeeModel findbyTaiKhoanID(int id){


        Cursor cursor= this.sqLiteDatabase.query("HOSONHANVIEN",null,null,null,null,null,null);
        try {
            EmployeeModel employeeModel=new EmployeeModel();
            if (cursor.moveToLast()) {
                String maHoso = cursor.getString(0);
                byte[] avatar = cursor.getBlob(1);
                String hoVaTen = cursor.getString(2);
                String maTK = cursor.getString(4);
                employeeModel = new EmployeeModel(maHoso, hoVaTen, avatar, maTK);
            }
            return employeeModel;
        }
        finally {
            cursor.close();
            sqLiteDatabase.close();
        }

    }

}
