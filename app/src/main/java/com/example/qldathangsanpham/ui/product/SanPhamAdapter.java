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
import com.example.qldathangsanpham.utillity.Utility;
import com.example.qldathangsanpham.model.SanPham;
import com.example.qldathangsanpham.ui.product.spinner.Country;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<SanPham> implements Filterable {
    Context context;
    List<SanPham> objects = new ArrayList<>();
    List<SanPham> filteredObjects = new ArrayList<>();

    private ItemFilter filter = new ItemFilter();

    public SanPhamAdapter(@NonNull Context context, List<SanPham> objects) {
        super(context, 0);
        this.context = context;

        setList(objects);
    }

    @Override
    public int getCount() {
        return filteredObjects.size();
    }

    @Nullable
    @Override
    public SanPham getItem(int position) {
        return filteredObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_san_pham_view, parent, false);
        }

        TextView tensp = convertView.findViewById(R.id.tenSP);
        TextView xuatXu = convertView.findViewById(R.id.xuatXu);
        TextView gia = convertView.findViewById(R.id.gia);

        SanPham sp = filteredObjects.get(position);

        tensp.setText(String.format("%s", sp.getTensp()));
//        xuatXu.setText(String.format("Xuất xứ: %s", Country.getCountryById(sp.getXuatXu()).getName()));
        gia.setText(Utility.showGia(sp.getGia()));

        return convertView;
    }

    public void setList(List<SanPham> list) {
        objects.clear();
        filteredObjects.clear();

        objects.addAll(list);
        filteredObjects.addAll(list);

        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String search = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            ArrayList<SanPham> nlist = new ArrayList<>();

            for (SanPham s : objects) {
                if (s.getTensp().toLowerCase().contains(search) || Country.getCountryById(s.getXuatXu()).getName().toLowerCase().contains(search)) {
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
            filteredObjects.clear();
            filteredObjects.addAll((ArrayList<SanPham>) results.values);

            notifyDataSetChanged();
        }

    }
}