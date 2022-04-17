package com.example.qldathangsanpham.ui.customer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

public class CustomerFormActivity extends AppCompatActivity {
    final int MyVersion = Build.VERSION.SDK_INT;
    String avatarPath;
    boolean isChangeAvatar = false;

    public void onLoadPicture(View view){
        //request permission to stored
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyHavePermission()) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                isChangeAvatar = true;
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setResult(Activity.RESULT_OK, i);
                //alter start activity for result
                someActivityResultLauncher.launch(i);
            }
        }
    }

    private boolean checkIfAlreadyHavePermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        Log.d("picturePath", picturePath);
                        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                        ImageView avatar = (ImageView) findViewById(R.id.avatar);
                        avatar.setImageBitmap(bitmap);
                    }
                }
            });


    public void onClickInsertOrUpdate(View view){
        SQLiteOpenHelper andDoDatabaseHelper =
                new DatabaseHelper(CustomerFormActivity.this);

        EditText tenKH = findViewById(R.id.tenKH);
        EditText diaChi = findViewById(R.id.diaChi);
        EditText soDT = findViewById(R.id.soDT);
        ImageView avatar = findViewById(R.id.avatar);
        Button btnAddOrUpdate = findViewById(R.id.btnInsert);

        String strTenKH =  tenKH.getText().toString();
        String strDiaChi =  diaChi.getText().toString();
        String strSoDT =  soDT.getText().toString();
        String strAddOrUpdate = btnAddOrUpdate.getText().toString();

        if (strAddOrUpdate.equals("Thêm")){
            SQLiteDatabase db = andDoDatabaseHelper.getWritableDatabase();
            int nextID = DatabaseHelper.nextAutoIncrement(db, "HoSoKhachHang");

            //save avatar to internal storage
            BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            ContextWrapper cw = new ContextWrapper(this.getApplicationContext());

            // data/user/0/com.example.qldathangsanpham/app_avatarCus/
            File directory = cw.getDir("avatarCus", Context.MODE_PRIVATE);
            // Create .jpg
            File myPath = new File(directory,nextID + ".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(myPath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                DatabaseHelper.insertCustomer(db, strTenKH, strDiaChi, strSoDT, myPath.getAbsolutePath());
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            } catch(SQLiteException e) {
                e.printStackTrace();
            }
        } else {
            //replace old image
            if (isChangeAvatar){
                BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                File myPath = new File(avatarPath);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(myPath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            EditText maKH = findViewById(R.id.maKH);
            String strMaKH =  maKH.getText().toString();

            try {
                SQLiteDatabase db = andDoDatabaseHelper.getWritableDatabase();
                DatabaseHelper.updateCustomer(db, strTenKH, strDiaChi, strSoDT, strMaKH);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            } catch(SQLiteException e) {
                e.printStackTrace();
            }
        }

    }

    public void onClickDelete(View view){
        SQLiteOpenHelper andDoDatabaseHelper =
                new DatabaseHelper(CustomerFormActivity.this);

        EditText maKH = findViewById(R.id.maKH);
        String strMaKH =  maKH.getText().toString();

        try {
            SQLiteDatabase db = andDoDatabaseHelper.getWritableDatabase();
            DatabaseHelper.deleteCustomer(db, strMaKH);
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        } catch(SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        int intMaKH = (Integer) getIntent().getExtras().get(CustomerActivity.EXTRA_MAKH);

        if (intMaKH==-1){
            //Insert mode
            EditText tvMaKH = findViewById(R.id.maKH);
            Button btnDelete = findViewById(R.id.btnDelete);

            tvMaKH.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            //Update mode
            //Create a cursor
            SQLiteOpenHelper angDoDatabaseHelper = new DatabaseHelper(this);
            try {
                SQLiteDatabase db = angDoDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query ("HoSoKhachHang", new String[] {"_id", "hoTen", "diaChi","sdt", "avatar"},
                        "_id=?",
                        new String[] {Integer.toString(intMaKH)},
                        null, null, null);
                if (cursor.moveToFirst()) {
                    String maKH = cursor.getString(0);
                    String tenKH = cursor.getString(1);
                    String diaChi = cursor.getString(2);
                    String soDT = cursor.getString(3);
                    avatarPath = cursor.getString(4);

                    EditText tvMaKH = findViewById(R.id.maKH);
                    tvMaKH.setText(maKH);

                    EditText tvtenKH = findViewById(R.id.tenKH);
                    tvtenKH.setText(tenKH);

                    EditText tvDiaChi = findViewById(R.id.diaChi);
                    tvDiaChi.setText(diaChi);

                    EditText tvSoDT = findViewById(R.id.soDT);
                    tvSoDT.setText(soDT);

                    ImageView ivAvatar = (ImageView)findViewById(R.id.avatar);
                    Log.d("avatarPathFromDB", avatarPath);
                    Bitmap bitmap = BitmapFactory.decodeFile(avatarPath);
                    ivAvatar.setImageBitmap(bitmap);
                    ivAvatar.setContentDescription("Avatar");

                    Button btnCustomerForm = findViewById(R.id.btnInsert);
                    btnCustomerForm.setText("Cập nhật");
                }
                cursor.close();;
                db.close();
            }
            catch (SQLiteException e){
                Toast toast = Toast.makeText(this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                isChangeAvatar = true;
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setResult(Activity.RESULT_OK, i);
                //alter start activity for result
                someActivityResultLauncher.launch(i);
            }
            else{
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}