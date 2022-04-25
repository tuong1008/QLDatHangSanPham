package com.example.qldathangsanpham.ui.order.oderhome;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private List<Map<String, String>> items;
    private Context context;

    public OrderAdapter(List<Map<String, String>> items) {
        this.items = items;
    }

//    public OrderAdapter(List<Map<String, String>> items, Context context) {
//        this.items = items;
//        this.context = context;
//    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(item);
    }

    public void filterList(List<Map<String, String>> filterllist) {
        items = filterllist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        TextView tvIdDdh = holder.tvIdDdh;
        TextView tvNgayDatHang = holder.tvNgayDatHang;
        TextView tvThoiGianDatHang = holder.tvThoiGianDatHang;
        TextView tvTenKhachHang = holder.tvTenKhachHang;

        tvIdDdh.setText(String.valueOf(items.get(position).get(DatabaseHelper.CL_ID)));
        tvNgayDatHang.setText(items.get(position).get(DatabaseHelper.CL_NGAY_DAT_HANG));
        tvThoiGianDatHang.setText(items.get(position).get("thoiGianDatHang"));
        tvTenKhachHang.setText(items.get(position).get(DatabaseHelper.CL_HO_TEN));

        int orderId = Integer.parseInt(Objects.requireNonNull(items.get(position).get(DatabaseHelper.CL_ID)));
        int customerId = Integer.parseInt(Objects.requireNonNull(items.get(position).get("customerId")));
        holder.itemView.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(OrderHomeFragmentDirections.actionEditOrder(orderId, customerId));
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Map<String, String> item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeFromDatabase(Context context, int position) {
        int orderId = Integer.parseInt(Objects.requireNonNull(items.get(position).get(DatabaseHelper.CL_ID)));
        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        DatabaseHelper.deleteDonDatHang(db, orderId);
    }
}
