package com.example.qldathangsanpham.ui.order.ordereditor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.ui.order.productadder.ProductAdderFragmentDirections;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductInOrderAdapter extends RecyclerView.Adapter<ProductInOrderViewHolder> {

    private List<Map<String, String>> items;
    private int orderId;
    private int customerId;
    private BottomSheetDialog dialog;


    public ProductInOrderAdapter(List<Map<String, String>> items) {
        this.items = items;
    }

    public ProductInOrderAdapter(List<Map<String, String>> items, int orderId, int customerId) {
        this.items = items;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    @NonNull
    @Override
    public ProductInOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_product_order, parent, false);
        return new ProductInOrderViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductInOrderViewHolder holder, int position) {
        TextView tvTenSanPham = holder.tvTenSanPham;
        TextView tvDonGia = holder.tvDonGia;
        TextView tvSoLuong = holder.tvSoLuong;

        tvTenSanPham.setText(items.get(position).get(DatabaseHelper.CL_TEN_SAN_PHAM));
        tvDonGia.setText(String.valueOf(items.get(position).get(DatabaseHelper.CL_DON_GIA)));
        tvSoLuong.setText(String.valueOf(items.get(position).get(DatabaseHelper.CL_SO_LUONG)));

        holder.itemView.setOnClickListener(view -> {
            View bsProductAdder = LayoutInflater.from(view.getContext()).inflate(R.layout.bottom_sheet_product, null);
            dialog = new BottomSheetDialog(view.getContext());

            Button tbAddProduct = bsProductAdder.findViewById(R.id.bt_add_product);
            TextView tvTenSP = bsProductAdder.findViewById(R.id.tv_ten_san_pham);
            TextView tvDonGiaSP = bsProductAdder.findViewById(R.id.tv_don_gia);
            AppCompatImageButton btIncrease = bsProductAdder.findViewById(R.id.bt_increase);
            AppCompatImageButton btDecrease = bsProductAdder.findViewById(R.id.bt_decrease);
            EditText etSoLuong = bsProductAdder.findViewById(R.id.et_so_luong_dat);

            tvTenSP.setText(items.get(position).get(DatabaseHelper.CL_TEN_SAN_PHAM));
            tvDonGiaSP.setText(String.valueOf(items.get(position).get(DatabaseHelper.CL_DON_GIA)));
            etSoLuong.setText(String.valueOf(items.get(position).get(DatabaseHelper.CL_SO_LUONG)));
            tbAddProduct.setText(R.string.luu);

            btDecrease.setOnClickListener(view1 -> {
                int value = Integer.parseInt(String.valueOf(etSoLuong.getText()));
                if (value > 1) {
                    etSoLuong.setText(String.valueOf(value - 1));
                }
            });
            btIncrease.setOnClickListener(view1 -> {
                int value = Integer.parseInt(String.valueOf(etSoLuong.getText()));
                etSoLuong.setText(String.valueOf(value + 1));
            });

            tvTenSP.setText(items.get(position).get(DatabaseHelper.CL_TEN_SAN_PHAM));
            tvDonGiaSP.setText(items.get(position).get(DatabaseHelper.CL_DON_GIA));

            dialog.setContentView(bsProductAdder);
            dialog.show();
            tbAddProduct.setOnClickListener(view1 -> {

                int productId = Integer.parseInt(Objects.requireNonNull(items.get(position).get(DatabaseHelper.CL_ID)));
                int soLuong = Integer.parseInt(String.valueOf(etSoLuong.getText()));
                SQLiteOpenHelper databaseHelper = new DatabaseHelper(view.getContext());
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                DatabaseHelper.updateCtDonDatHang(db, orderId, productId, soLuong);
                Navigation.findNavController(view).navigate(OrderEditorFragmentDirections.actionUpdateSoLuong(orderId, customerId));
                dialog.dismiss();
            });
        });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void restoreItem(Map<String, String> item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void removeFromDatabase(Context context, int orderId, int position) {
        int productId = Integer.parseInt(Objects.requireNonNull(items.get(position).get(DatabaseHelper.CL_ID)));
        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        DatabaseHelper.deteleCtDonDatHang(db, orderId, productId);
    }
    public void filterList(List<Map<String, String>> filterllist) {
        items = filterllist;
        notifyDataSetChanged();
    }
}
