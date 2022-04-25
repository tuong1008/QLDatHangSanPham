package com.example.qldathangsanpham.ui.order.productadder;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private int customerId;
    private ProductAdderAdapter adapter;
    private RecyclerView rvOrder;
    private Context context;
    private List<Map<String, String>> items;


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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_product_adder, container, false);
        orderId = ProductAdderFragmentArgs.fromBundle(getArguments()).getOrderId();
        customerId = ProductAdderFragmentArgs.fromBundle(getArguments()).getCustomerId();
        items = DatabaseHelper.findProductNotInOrder(view.getContext(), orderId);
        RecyclerView rvOrder = view.findViewById(R.id.rv_product_adder);
        ProductAdderAdapter adapter = new ProductAdderAdapter(items, customerId, orderId);
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
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