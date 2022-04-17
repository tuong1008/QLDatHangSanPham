package com.example.qldathangsanpham.ui.product;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;

import java.text.DecimalFormat;

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

        // intent is to edit/delete
        if (getIntent().hasExtra("SAN_PHAM")) {
            them.setText("Sửa");

            SanPham sp = (SanPham) getIntent().getSerializableExtra("SAN_PHAM");

            tensp.setText(sp.getTensp());
            xuatXu.setText(sp.getXuatXu());

            DecimalFormat format = new DecimalFormat("0.#");
            gia.setText(format.format(sp.getGia()));

            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        sp.setTensp(String.valueOf(tensp.getText()).trim());
                        sp.setXuatXu(String.valueOf(xuatXu.getText()).trim());
                        sp.setGia(Double.parseDouble(String.valueOf(gia.getText()).trim()));

                        db.updateSanPham(sp);
                        Toast.makeText(view.getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    } finally {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteSanPham(sp.getMasp());

                    Toast.makeText(view.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            xoa.setVisibility(View.INVISIBLE);
            them.setText("Thêm");

            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SanPham sp = new SanPham();

                    try {
                        sp.setTensp(String.valueOf(tensp.getText()).trim());
                        sp.setXuatXu(String.valueOf(xuatXu.getText()).trim());
                        sp.setGia(Double.valueOf(String.valueOf(gia.getText()).trim()));

                        db.addSanPham(sp);

                        Toast.makeText(view.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    } finally {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            });

        }
    }


}