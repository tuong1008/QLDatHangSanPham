package com.example.qldathangsanpham.ui.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.Utility;
import com.example.qldathangsanpham.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<SanPham> implements Filterable {
    Context context;
    int resource;
    List<SanPham> objects;
    List<SanPham> filteredObjects;
    private ItemFilter filter = new ItemFilter();

    public SanPhamAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

        this.objects = objects;
        this.filteredObjects = objects;
//        setList(objects);
    }

    @Override
    public int getCount() {
        return filteredObjects.size();
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
        this.filteredObjects = list;
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String search = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SanPham> list = objects;

            int count = list.size();
            final ArrayList<SanPham> nlist = new ArrayList<SanPham>(count);

            for (SanPham s : objects) {
                if (s.getTensp().toLowerCase().trim().contains(search) ||
                        s.getXuatXu().toLowerCase().trim().contains(search)) {
                    nlist.add(s);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredObjects = (ArrayList<SanPham>) results.values;
            notifyDataSetChanged();
        }

    }
}