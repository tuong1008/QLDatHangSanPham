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

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private List<Map<String, String>> orders;

    public OrderAdapter(List<Map<String, String>> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View orderView = inflater.inflate(R.layout.item_order, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(orderView);
        return orderViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        TextView tvIdDdh = holder.tvIdDdh;
        TextView tvNgayDatHang = holder.tvNgayDatHang;
        TextView tvThoiGianDatHang = holder.tvThoiGianDatHang;
        TextView tvTenKhachHang = holder.tvTenKhachHang;

        tvIdDdh.setText(String.valueOf(orders.get(position).get(DatabaseHelper.CL_ID)));
        tvNgayDatHang.setText(orders.get(position).get(DatabaseHelper.CL_NGAY_DAT_HANG));
        tvThoiGianDatHang.setText(orders.get(position).get("thoiGianDatHang"));
        tvTenKhachHang.setText(orders.get(position).get(DatabaseHelper.CL_HO_TEN));

    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }
}
