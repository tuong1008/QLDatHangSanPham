package com.example.qldathangsanpham.ui.order.ordereditor;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.R;

public class ProductInOrderViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTenSanPham, tvDonGia, tvSoLuong;

    public ProductInOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTenSanPham = itemView.findViewById(R.id.tv_item_product_order_ten_san_pham);
        tvDonGia = itemView.findViewById(R.id.tv_item_product_order_don_gia);
        tvSoLuong = itemView.findViewById(R.id.tv_product_oder_so_luong);
    }
}
