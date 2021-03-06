package com.example.qldathangsanpham.ui.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;
import com.example.qldathangsanpham.ui.product.spinner.Country;
import com.example.qldathangsanpham.ui.product.spinner.CountryAdapter;

import java.text.DecimalFormat;

public class FormSanPham extends AppCompatActivity {
    EditText tensp, gia;
    Spinner xuatXu;
    Button them, xoa;

    DatabaseHelper db;

    CountryAdapter countryAdapter;

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

        countryAdapter = new CountryAdapter(this);
        xuatXu.setAdapter(countryAdapter);

        // intent is to edit
        if (getIntent().hasExtra("SAN_PHAM")) {
            them.setText("Sửa");

            SanPham sp = (SanPham) getIntent().getSerializableExtra("SAN_PHAM");

            tensp.setText(sp.getTensp());

            xuatXu.setSelection(Country.valueOf(sp.getXuatXu()).ordinal() + 1);

            DecimalFormat format = new DecimalFormat("0.#");
            gia.setText(format.format(sp.getGia()));

            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checkFields()) {
                        Toast.makeText(view.getContext(), "Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        Country c = (Country) xuatXu.getSelectedItem();

                        sp.setTensp(String.valueOf(tensp.getText()).trim());
                        sp.setGia(Double.parseDouble(String.valueOf(gia.getText()).trim()));
                        sp.setXuatXu(c.getId());

                        if (db.updateSanPham(sp)) {
                            Toast.makeText(view.getContext(), "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Hãy điền lại thông tin", Toast.LENGTH_SHORT).show();
                    } finally {
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int option) {
                            switch (option) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    // yes
                                    if (db.deleteSanPham(sp.getMasp())) {
                                        Toast.makeText(view.getContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(), "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                    setResult(RESULT_OK);
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    // no
                                    break;
                            }
                        }
                    };

                    // show confirm delete dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Xác nhận xóa?")
                            .setPositiveButton("Có", dialogClickListener)
                            .setNegativeButton("Không", dialogClickListener)
                            .show();
                }
            });
        } else {
            // add san pham
            xoa.setVisibility(View.INVISIBLE);
            them.setText("Thêm");
            xuatXu.setSelection(0);

            them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checkFields()) {
                        Toast.makeText(view.getContext(), "Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SanPham sp = new SanPham();

                    try {
                        Country c = (Country) xuatXu.getSelectedItem();

                        sp.setTensp(String.valueOf(tensp.getText()).trim());
                        sp.setXuatXu(String.valueOf(c.getId()));
                        sp.setGia(Double.valueOf(String.valueOf(gia.getText()).trim()));

                        if (db.addSanPham(sp)) {
                            Toast.makeText(view.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Hãy điền lại thông tin", Toast.LENGTH_SHORT).show();
                    } finally {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            });
        }
    }

    private boolean checkFields() {
        String name = String.valueOf(tensp.getText()).trim();
        String price = String.valueOf(gia.getText()).trim();

        return !name.isEmpty() && countryAdapter.isEnabled(xuatXu.getSelectedItemPosition()) && !price.isEmpty();
    }
}