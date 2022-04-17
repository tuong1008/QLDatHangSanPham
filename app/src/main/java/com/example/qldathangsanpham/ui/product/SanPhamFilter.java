package com.example.qldathangsanpham.ui.product;

import android.widget.Filter;

import com.example.qldathangsanpham.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamFilter extends Filter {

    private List<SanPham> list;
    private List<SanPham> filteredList;
    private SanPhamAdapter adapter;

    public SanPhamFilter(List<SanPham> list, SanPhamAdapter adapter) {
        this.list = list;
        this.adapter = adapter;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        filteredList.clear();
        FilterResults filterResults = new FilterResults();

        String search = charSequence.toString().toLowerCase();

        if (search.trim().isEmpty()) {
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        for (SanPham s : list) {
            if (s.getTensp().toLowerCase().trim().contains(search)) {
                filteredList.add(s);
            }
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setList(filteredList);
        adapter.notifyDataSetChanged();
    }
}