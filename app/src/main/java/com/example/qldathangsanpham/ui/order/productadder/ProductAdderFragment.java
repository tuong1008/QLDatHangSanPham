package com.example.qldathangsanpham.ui.order.productpicker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductAdderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductAdderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int orderId;

    public ProductAdderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderEditorAddProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductAdderFragment newInstance(String param1, String param2) {
        ProductAdderFragment fragment = new ProductAdderFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_adder, container, false);
        orderId = ProductAdderFragmentArgs.fromBundle(getArguments()).getOrderId();
        RecyclerView rvOrder = view.findViewById(R.id.rv_product_adder);
        ProductAdderAdapter adapter = new ProductAdderAdapter(DatabaseHelper.findProductInOrder(view.getContext(), 0));
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

//    private int insertDDH(Context context) {
//        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//        return DatabaseHelper.insertDonDatHang(db, Utility.getCurrentDateTime(), )
//    }

}