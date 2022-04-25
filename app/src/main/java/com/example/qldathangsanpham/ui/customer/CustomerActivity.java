package com.example.qldathangsanpham.ui.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.MainActivity;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.KhachHang;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class CustomerActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor customersCursor;
    public static  final String EXTRA_MAKH = "maKH";

    int pageHeight = 1120;
    int pagewidth = 792;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    public void onClickAdd(View view){
        Intent intent = new Intent(this, CustomerFormActivity.class);
        intent.putExtra(EXTRA_MAKH, -1);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setCustomersListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor newCursor = db.query("HoSoKhachHang", new String[]{"_id", "hoTen"},
                null, null, null, null, null);

        ListView listFavorites = findViewById(R.id.list);
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        customersCursor = newCursor;
    }


    private void setCustomersListView(){
        ListView listCustomers = findViewById(R.id.list);
        try{
            SQLiteOpenHelper angDoDatabaseHelper = new DatabaseHelper(this);
            db = angDoDatabaseHelper.getReadableDatabase();
            customersCursor = db.query("HoSoKhachHang", new String[]{"_id", "hoTen"},
                    null, null, null, null, null);
            CursorAdapter customerAdapter =
                    new SimpleCursorAdapter(CustomerActivity.this, android.R.layout.simple_list_item_1, customersCursor, new String[]{"hoTen"}, new int[]{android.R.id.text1},0);
            listCustomers.setAdapter(customerAdapter);

//            String tableName = "KhachHang";
//            Log.d("LogCursor", "getTableAsString called");
//            String tableString = String.format("Table %s:\n", tableName);
//            Cursor allRows  = customersCursor;
//            if (allRows.moveToFirst() ){
//                String[] columnNames = allRows.getColumnNames();
//                do {
//                    for (String name: columnNames) {
//                        tableString += String.format("%s: %s\n", name,
//                                allRows.getString(allRows.getColumnIndex(name)));
//                    }
//                    tableString += "\n";
//
//                } while (allRows.moveToNext());
//                Log.d("LogCursor", tableString);
//            }
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
        listCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CustomerActivity.this, CustomerFormActivity.class);
                intent.putExtra(EXTRA_MAKH, (int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.export_pdf:
                if (checkPermission()) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    generatePDF();
                } else {
                    requestPermission();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void generatePDF() {
        Document document = new Document();
        try{
            File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
            PdfWriter.getInstance(document,
                    new FileOutputStream(file));

            document.open();
            // Table
            PdfPTable table = new PdfPTable(5);

            // Header
            PdfPCell cell1 = new PdfPCell(new Phrase("ID"));
            PdfPCell cell2 = new PdfPCell(new Phrase("Ho ten"));
            PdfPCell cell3 = new PdfPCell(new Phrase("Đia chi"));
            PdfPCell cell4 = new PdfPCell(new Phrase("So dien thoai"));
            PdfPCell cell5 = new PdfPCell(new Phrase("Avatar"));
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);

            DatabaseHelper angDoDatabaseHelper = new DatabaseHelper(this);
            db = angDoDatabaseHelper.getReadableDatabase();
            List<KhachHang> allCustomers = angDoDatabaseHelper.getAllCustomers(db);

            for (KhachHang i : allCustomers){
                Image image = Image.getInstance(i.getAvatar());
                image.scaleAbsolute(70f, 70f);
                PdfPCell cell1x = new PdfPCell(new Phrase(String.valueOf(i.get_id())));
                PdfPCell cell2x = new PdfPCell(new Phrase(i.getHoTen()));
                PdfPCell cell3x = new PdfPCell(new Phrase(i.getDiaChi()));
                PdfPCell cell4x = new PdfPCell(new Phrase(i.getSdt()));
                PdfPCell cell5x = new PdfPCell(image, false);
                table.addCell(cell1x);
                table.addCell(cell2x);
                table.addCell(cell3x);
                table.addCell(cell4x);
                table.addCell(cell5x);
            }
            document.add(table);

            document.close();
            Toast.makeText(this, "Create PDF successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}