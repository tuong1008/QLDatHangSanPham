package com.example.qldathangsanpham.ui.order.oderhome;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView tvIdDdh, tvNgayDatHang, tvTenKhachHang, tvThoiGianDatHang;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        tvIdDdh = itemView.findViewById(R.id.tv_id_ddh);
        tvNgayDatHang = itemView.findViewById(R.id.tv_ngay_dat_hang);
        tvThoiGianDatHang = itemView.findViewById(R.id.tv_thoi_gian_dat_hang);
        tvTenKhachHang = itemView.findViewById(R.id.tv_item_order_ten_khach_hang);
    }
}
