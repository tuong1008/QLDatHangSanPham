package com.example.qldathangsanpham.ui.order;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private int orderId;
    private AutoCompleteTextView actvSoDienThoai;
    private EditText etHoTen, etDiaChi;
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
        View view = inflater.inflate(R.layout.fragment_order_editor, container, false);
        RecyclerView rvProductInOrder = view.findViewById(R.id.rv_products_in_order);
        orderId = OrderEditorFragmentArgs.fromBundle(getArguments()).getOrderId();
        ProductInOrderAdapter adapter = new ProductInOrderAdapter(DatabaseHelper.findProductInOrder(view.getContext(), orderId));
        rvProductInOrder.setAdapter(adapter);
        rvProductInOrder.setLayoutManager(new LinearLayoutManager(view.getContext()));
        FloatingActionButton fbtAddProduct = view.findViewById(R.id.fab_add_product);
        fbtAddProduct.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(OrderEditorFragmentDirections.actionAddProductToOrder(orderId));
        });
        return view;
    }


    private void actvSoDienThoaiSetUp() {

    }

    private boolean validation() {
        return true;
    }
}