package com.example.qldathangsanpham.ui.product;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;

public class FormSanPham extends AppCompatActivity {
    EditText tensp, xuatXu, gia;
    Button them, xoa;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_form);

        initComponents();
    }

    private void initComponents() {
        db = new DatabaseHelper(this);

        tensp = findViewById(R.id.tenSP);
        xuatXu = findViewById(R.id.xuatXu);
        gia = findViewById(R.id.gia);
        them = findViewById(R.id.btnInsert);
        xoa = findViewById(R.id.btnDelete);

        // intent is to edit
        if (getIntent().hasExtra("SAN_PHAM")) {
            them.setText("Sửa");

            SanPham sp = (SanPham) getIntent().getSerializableExtra("SAN_PHAM");

            tensp.setText(sp.getTensp());
            xuatXu.setText(sp.getTensp());
            gia.setText(sp.getTensp());

            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: edit sp
                    sp.setTensp(String.valueOf(tensp.getText()));
                    sp.setXuatXu(String.valueOf(xuatXu.getText()));
                    sp.setGia(Double.valueOf(String.valueOf(gia.getText())));

                    db.updateSanPham(sp);
                }
            });

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: xoa sp
                    db.deleteSanPham(sp.getMasp());
                }
            });
        } else {
            xoa.setVisibility(View.INVISIBLE);
            them.setText("Thêm");

            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SanPham sp = new SanPham();

                    sp.setTensp(String.valueOf(tensp.getText()));
                    sp.setXuatXu(String.valueOf(xuatXu.getText()));
                    sp.setGia(Double.valueOf(String.valueOf(gia.getText())));

                    db.addSanPham(sp);
                }
            });

        }
    }


}