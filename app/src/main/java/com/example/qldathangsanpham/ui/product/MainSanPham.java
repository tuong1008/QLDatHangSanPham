package com.example.qldathangsanpham.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainSanPham extends AppCompatActivity {
    private static final String TAG = MainSanPham.class.getName();

    public static List<SanPham> sanPhamList;
    DatabaseHelper db;

    SanPhamAdapter adapter;
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
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // There are no request codes
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        sanPhamList = db.getAllSanPham();
                        adapter.setList(sanPhamList);
                    }
                }
            });

    private void initComponents() {
        db = new DatabaseHelper(this);
        sanPhamList = db.getAllSanPham();

        Log.d(TAG, sanPhamList.toString());

        list = findViewById(R.id.list);
        add = findViewById(R.id.btnInsert);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        adapter = new SanPhamAdapter(this, R.layout.activity_san_pham_view, sanPhamList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainSanPham.this, FormSanPham.class);
                intent.putExtra("SAN_PHAM", sanPhamList.get(position));
                someActivityResultLauncher.launch(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FormSanPham.class);
                someActivityResultLauncher.launch(intent);
            }
        });
    }
}