package com.example.qldathangsanpham.ui.order.customerpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomerPickerAdapter extends RecyclerView.Adapter<CustomerPickerViewHolder> {

    private List<Map<String, String>> items;
    private int orderId;


    public CustomerPickerAdapter(List<Map<String, String>> items, int orderId) {
        this.items = items;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public CustomerPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_customer_picker, parent, false);
        return new CustomerPickerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPickerViewHolder holder, int position) {
        TextView tvHoTen = holder.tvHoTen;
        TextView tvSoDienThoai = holder.tvSoDienThoai;
        TextView tvDiaChi = holder.tvDiaChi;

        tvHoTen.setText(items.get(position).get(DatabaseHelper.CL_HO_TEN));
        tvSoDienThoai.setText(items.get(position).get(DatabaseHelper.CL_SDT));
        tvDiaChi.setText(items.get(position).get(DatabaseHelper.CL_DIA_CHI));

        holder.itemView.setOnClickListener(view -> {
            if (orderId == 0) {
                Navigation.findNavController(view).navigate(CustomerPickerFragmentDirections.actionCreateToOrder(Integer.parseInt(Objects.requireNonNull(items.get(position).get(DatabaseHelper.CL_ID)))));
            } else {
                Navigation.findNavController(view).navigate(CustomerPickerFragmentDirections.actionChangeCustomer(orderId, Integer.parseInt(Objects.requireNonNull(items.get(position).get(DatabaseHelper.CL_ID)))));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void filterList(List<Map<String, String>> filterllist) {
        items = filterllist;
        notifyDataSetChanged();
    }
}
