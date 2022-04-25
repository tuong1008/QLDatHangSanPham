package com.example.qldathangsanpham.ui.order.customerpicker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.R;


public class CustomerPickerViewHolder extends RecyclerView.ViewHolder {
    public TextView tvHoTen, tvSoDienThoai, tvDiaChi;

    public CustomerPickerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHoTen = itemView.findViewById(R.id.tv_item_customer_picker_ten_khach_hang);
        tvSoDienThoai = itemView.findViewById(R.id.tv_item_customer_picker_so_dien_thoai);
        tvDiaChi = itemView.findViewById(R.id.tv_item_customer_picker_dia_chi);
    }
}
