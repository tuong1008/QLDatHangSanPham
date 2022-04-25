package com.example.qldathangsanpham.ui.order.productadder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.Utility;
import com.example.qldathangsanpham.ui.order.ordereditor.OrderEditorFragmentArgs;
import com.example.qldathangsanpham.ui.order.ordereditor.OrderEditorFragmentDirections;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductAdderAdapter extends RecyclerView.Adapter<ProductAdderViewHolder> {

    private List<Map<String, String>> items;
    private int customerId;
    private int orderId;
    private BottomSheetDialog dialog;


    public ProductAdderAdapter(List<Map<String, String>> items, int customerId, int orderId) {
        this.items = items;
        this.customerId = customerId;
        this.orderId = orderId;
    }

    @NonNull
    @Override
    public ProductAdderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_product_adder, parent, false);
        return new ProductAdderViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdderViewHolder holder, int position) {
        TextView tvTenSanPham = holder.tvTenSanPham;
        TextView tvDonGia = holder.tvDonGia;

        tvTenSanPham.setText(items.get(position).get(DatabaseHelper.CL_TEN_SAN_PHAM));
        tvDonGia.setText(items.get(position).get(DatabaseHelper.CL_DON_GIA));

        holder.itemView.setOnClickListener(view -> {
            View bsProductAdder = LayoutInflater.from(view.getContext()).inflate(R.layout.bottom_sheet_product, null);
            dialog = new BottomSheetDialog(view.getContext());

            Button tbAddProduct = bsProductAdder.findViewById(R.id.bt_add_product);
            TextView tvTenSP = bsProductAdder.findViewById(R.id.tv_ten_san_pham);
            TextView tvDonGiaSP = bsProductAdder.findViewById(R.id.tv_don_gia);
            AppCompatImageButton btIncrease = bsProductAdder.findViewById(R.id.bt_increase);
            AppCompatImageButton btDecrease = bsProductAdder.findViewById(R.id.bt_decrease);
            EditText etSoLuong = bsProductAdder.findViewById(R.id.et_so_luong_dat);
            etSoLuong.setText(String.valueOf(1));
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
                SQLiteOpenHelper databaseHelper = new DatabaseHelper(view.getContext());
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                DatabaseHelper.insertCtDonDatHang(db, orderId, productId, Integer.parseInt(String.valueOf(etSoLuong.getText())));
                dialog.dismiss();
                Navigation.findNavController(view).navigate(ProductAdderFragmentDirections.actionBackToOrder(customerId, orderId));
            });
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
