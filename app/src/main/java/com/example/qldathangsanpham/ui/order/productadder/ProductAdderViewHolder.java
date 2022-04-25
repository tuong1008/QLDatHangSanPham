package com.example.qldathangsanpham.ui.order.productadder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.R;

public class ProductAdderViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTenSanPham, tvDonGia;

    public ProductAdderViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTenSanPham = itemView.findViewById(R.id.tv_item_product_adder_ten_san_pham);
        tvDonGia = itemView.findViewById(R.id.tv_item_product_adder_don_gia);
    }
}
