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
import com.example.qldathangsanpham.Utility;
import com.example.qldathangsanpham.model.SanPham;

import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    List<SanPham> objects;
    SanPhamFilter filter;

    public SanPhamAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

        this.filter = new SanPhamFilter(objects, this);
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

        tensp.setText(String.format("%s", sp.getTensp()));
        xuatXu.setText(String.format("Xuất xứ: %s", sp.getXuatXu()));
        gia.setText(Utility.showGia(sp.getGia()));

        return convertView;
    }

    public void setList(List<SanPham> list) {
        this.objects = list;
        notifyDataSetChanged();
    }

    public void filterList(String text) {
        filter.filter(text);
    }
}