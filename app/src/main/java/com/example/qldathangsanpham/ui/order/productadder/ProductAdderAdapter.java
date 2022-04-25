package com.example.qldathangsanpham.ui.order.productpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

import java.util.List;
import java.util.Map;

public class ProductAdderAdapter extends RecyclerView.Adapter<ProductAdderViewHolder> {

    private List<Map<String, String>> productAdder;

    public ProductAdderAdapter(List<Map<String, String>> productAdder) {
        this.productAdder = productAdder;
    }

    @NonNull
    @Override
    public ProductAdderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productInOrder = inflater.inflate(R.layout.item_product_adder, parent, false);
        return new ProductAdderViewHolder(productInOrder);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdderViewHolder holder, int position) {
        TextView tvTenSanPham = holder.tvTenSanPham;
        TextView tvDonGia = holder.tvDonGia;

        tvTenSanPham.setText(productAdder.get(position).get(DatabaseHelper.CL_TEN_SAN_PHAM));
        tvDonGia.setText(productAdder.get(position).get(DatabaseHelper.CL_DON_GIA));
    }

    @Override
    public int getItemCount() {
        return this.productAdder.size();
    }
}
