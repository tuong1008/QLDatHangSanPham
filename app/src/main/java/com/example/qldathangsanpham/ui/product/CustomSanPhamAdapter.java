package com.example.qldathangsanpham.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;

import java.util.List;

public class CustomSanPhamAdapter extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    List<SanPham> objects;

    public CustomSanPhamAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tensp = convertView.findViewById(R.id.tenSP);
        TextView xuatXu = convertView.findViewById(R.id.xuatXu);
        TextView gia = convertView.findViewById(R.id.gia);

        SanPham sp = objects.get(position);

        tensp.setText(sp.getTensp());
        xuatXu.setText(sp.getXuatXu());
        gia.setText(String.valueOf(sp.getGia()));

        return convertView;
    }
}