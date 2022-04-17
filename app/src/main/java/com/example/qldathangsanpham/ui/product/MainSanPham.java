package com.example.qldathangsanpham.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainSanPham extends AppCompatActivity {
    private static final String TAG = MainSanPham.class.getName();

    public static List<SanPham> sanPhamList;
    DatabaseHelper db;

    CustomSanPhamAdapter adapter;
    ListView list;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_main);

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        initComponents();
//        adapter = (CustomSanPhamAdapter) list.getAdapter();
//        sanPhamList = db.getAllSanPham();
//        adapter.setNotifyOnChange(true);
//        sanPhamList = db.getAllSanPham();
    }

    private void initComponents() {
        db = new DatabaseHelper(this);
        sanPhamList = db.getAllSanPham();

        list = findViewById(R.id.list);
        add = findViewById(R.id.btnInsert);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        adapter = new CustomSanPhamAdapter(this, R.layout.activity_san_pham_view, sanPhamList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainSanPham.this, FormSanPham.class);
                intent.putExtra("SAN_PHAM", sanPhamList.get(position));
                startActivity(intent);

                sanPhamList = db.getAllSanPham();
                adapter.notifyDataSetChanged();
            }
        });
    }
}