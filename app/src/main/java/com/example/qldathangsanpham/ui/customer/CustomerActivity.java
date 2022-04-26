package com.example.qldathangsanpham.ui.customer;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.KhachHang;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    DatabaseHelper angDoDatabaseHelper;
    private SQLiteDatabase db;
    SectionsPagerAdapter pagerAdapter;
    ViewPager2 pager;
    public static final String EXTRA_MAKH = "maKH";
    public String[] tabTitles = {"Khách hàng", "Biểu đồ"};
    boolean runResume = false;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        angDoDatabaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getLifecycle());
        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CustomerFragment.customerAdapter.getFilter().filter(newText);
                pagerAdapter.notifyDataSetChanged();
                Toast.makeText(CustomerActivity.this, "text in search changed", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (runResume) {
            CustomerFragment.setCustomersList(angDoDatabaseHelper.getAllCustomers());

            CustomerFragment.customerAdapter.setItems(CustomerFragment.customersList);
            pagerAdapter.notifyItemChanged(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        runResume = true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
        try {
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

            List<KhachHang> allCustomers = angDoDatabaseHelper.getAllCustomers();

            for (KhachHang i : allCustomers) {
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
        } catch (Exception e) {
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