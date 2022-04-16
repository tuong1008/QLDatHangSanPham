package com.example.qldathangsanpham.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qldathangsanpham.R;

public class CustomerFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        //Create a cursor
        SQLiteOpenHelper angDoDatabaseHelper = new AngDoDatabaseHelper(this);
        try {
            SQLiteDatabase db = angDoDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("KHACHHANG", new String[]{"maKH", "tenKH", "diaChi", "soDT", "avatar"},
                    null,
                    null,
                    null, null, null);
            if (cursor.moveToFirst()) {
                String maKH = cursor.getString(0);
                String tenKH = cursor.getString(1);
                String diaChi = cursor.getString(2);
                String soDT = cursor.getString(3);
                int resourceId = cursor.getInt(4);

                TextView tvMaKH = (TextView) findViewById(R.id.maKH);
                tvMaKH.setText(maKH);

                TextView tvtenKH = (TextView) findViewById(R.id.tenKH);
                tvtenKH.setText(tenKH);

                TextView tvDiaChi = (TextView) findViewById(R.id.diaChi);
                tvDiaChi.setText(diaChi);

                TextView tvSoDT = (TextView) findViewById(R.id.soDT);
                tvSoDT.setText(soDT);

                ImageView ivAvatar = (ImageView) findViewById(R.id.avatar);
                ivAvatar.setImageResource(resourceId);
                ivAvatar.setContentDescription("Avatar");
            }
            cursor.close();
            ;
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }
}