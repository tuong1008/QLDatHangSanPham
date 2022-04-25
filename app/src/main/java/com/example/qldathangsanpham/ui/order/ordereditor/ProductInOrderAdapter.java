package com.example.qldathangsanpham.ui.order;

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

public class ProductInOrderAdapter extends RecyclerView.Adapter<ProductInOrderViewHolder> {

    private List<Map<String, String>> productInOrders;

    public ProductInOrderAdapter(List<Map<String, String>> productInOrders) {
        this.productInOrders = productInOrders;
    }

    @NonNull
    @Override
    public ProductInOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productInOrder = inflater.inflate(R.layout.item_product_order, parent, false);
        return new ProductInOrderViewHolder(productInOrder);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductInOrderViewHolder holder, int position) {
        TextView tvTenSanPham = holder.tvTenSanPham;
        TextView tvDonGia = holder.tvDonGia;
        TextView tvSoLuong = holder.tvSoLuong;

        tvTenSanPham.setText(productInOrders.get(position).get(DatabaseHelper.CL_TEN_SAN_PHAM));
        tvDonGia.setText(String.valueOf(productInOrders.get(position).get(DatabaseHelper.CL_DON_GIA)));
        tvSoLuong.setText(String.valueOf(productInOrders.get(position).get(DatabaseHelper.CL_SO_LUONG)));
    }

    @Override
    public int getItemCount() {
        return this.productInOrders.size();
    }
}
