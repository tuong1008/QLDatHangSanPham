package com.example.qldathangsanpham.ui.order.ordereditor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.Utility;
import com.example.qldathangsanpham.ui.order.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderEditorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int orderId, customerId;
    private TextView tvHoTen, tvSoDienThoai, tvDiaChi;
    private View view;
    private Context context;
    private RecyclerView rvProductInOrder;
    private ProductInOrderAdapter adapter;
    private List<Map<String, String>> items;
//    private OrderEditorEvents orderEditorEvents;

    public OrderEditorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderEditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderEditorFragment newInstance(String param1, String param2) {
        OrderEditorFragment fragment = new OrderEditorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

    }

//    @Override
//    public void onAttach(@NonNull Activity activity) {
//        super.onAttach(activity);
//        // the callback interface. If not, it throws an exception
//        try {
//            orderEditorEvents = (OrderEditorEvents) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement TextClicked");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_editor, container, false);
        this.context = container.getContext();
        orderId = OrderEditorFragmentArgs.fromBundle(getArguments()).getOrderId();
        customerId = OrderEditorFragmentArgs.fromBundle(getArguments()).getCustomerId();

        if (orderId == 0) {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(view.getContext());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            orderId = DatabaseHelper.insertDonDatHang(db, Utility.getCurrentDateTime(), 1, customerId);
        } else {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(view.getContext());
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            DatabaseHelper.updateCustomerFromDonDatHang(db, orderId, customerId);
        }
        customerSetup(view, customerId);
        items = DatabaseHelper.findProductInOrder(context, orderId);
        rvProductInOrder = view.findViewById(R.id.rv_products_in_order);
        adapter = new ProductInOrderAdapter(items, orderId, customerId);
        rvProductInOrder.setAdapter(adapter);
        rvProductInOrder.setLayoutManager(new LinearLayoutManager(context));
        enableSwipeToDeleteAndUndo();
        FloatingActionButton fbtAddProduct = view.findViewById(R.id.fab_add_product);
        fbtAddProduct.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(OrderEditorFragmentDirections.actionAddProductToOrder(orderId, customerId));
        });
        View customer = view.findViewById(R.id.layout_khach_hang);
        customer.setOnClickListener(view -> {
                    Navigation.findNavController(view).navigate(OrderEditorFragmentDirections.actionChangeCustomer(orderId));
                }
        );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    private void customerSetup(View view, int customerId) {
        View layoutCustomer = view.findViewById(R.id.layout_khach_hang);
        tvHoTen = layoutCustomer.findViewById(R.id.tv_item_customer_picker_ten_khach_hang);
        tvSoDienThoai = layoutCustomer.findViewById(R.id.tv_item_customer_picker_so_dien_thoai);
        tvDiaChi = layoutCustomer.findViewById(R.id.tv_item_customer_picker_dia_chi);

        Map<String, String> map = DatabaseHelper.findCustomerById(view.getContext(), customerId);

        tvHoTen.setText(map.get(DatabaseHelper.CL_HO_TEN));
        tvSoDienThoai.setText(map.get(DatabaseHelper.CL_SDT));
        tvDiaChi.setText(map.get(DatabaseHelper.CL_DIA_CHI));
//        System.out.println("khách hàng: " + customerId + tvHoTen.getText());
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Map<String, String> item = adapter.getItems().get(position);

                adapter.removeItem(position);

                Snackbar snackbar = Snackbar.make(view, "Đã xóa sản phẩm.", Snackbar.LENGTH_LONG);
                snackbar.setAction("HOÀN TÁC", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.restoreItem(item, position);
                        rvProductInOrder.scrollToPosition(position);
                    }
                });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        adapter.removeFromDatabase(context, orderId, position);
                    }
                });

                snackbar.setActionTextColor(Color.CYAN);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvProductInOrder);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });
    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        List<Map<String, String>> filterList = new ArrayList<>();

        // running a for loop to compare elements.
        for (Map<String, String> item : items) {
            if (item.get(DatabaseHelper.CL_TEN_SAN_PHAM).toLowerCase().contains(text.toLowerCase())
                    || item.get(DatabaseHelper.CL_DON_GIA).toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this.context, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filterList);
        }
    }
}