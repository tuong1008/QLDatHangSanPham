package com.example.qldathangsanpham.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.data.model.EmployeeModel;

public class EmployeeDataSoure {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    public EmployeeDataSoure(Context context) {
        this.context=context;
        this.sqLiteOpenHelper = new DatabaseHelper(this.context);
        this.sqLiteDatabase=this.sqLiteOpenHelper.getReadableDatabase();

    }

    public EmployeeModel findbyTaiKhoanID(int id){


        Cursor cursor= this.sqLiteDatabase.query("HOSONHANVIEN",null,null,null,null,null,null);
        try {
            EmployeeModel employeeModel=new EmployeeModel();
            if (cursor.moveToLast()) {
                String maHoso = cursor.getString(0);
                byte[] avatar = cursor.getBlob(3);
                String hoVaTen = cursor.getString(1);
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
