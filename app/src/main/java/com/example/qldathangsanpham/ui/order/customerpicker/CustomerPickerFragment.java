package com.example.qldathangsanpham.ui.order.customerpicker;

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
 * Use the {@link CustomerPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerPickerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvCustomerPicker;
    private CustomerPickerAdapter adapter;
    private List<Map<String, String>> items;
    private Context context;

    public CustomerPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerPickerFragment newInstance(String param1, String param2) {
        CustomerPickerFragment fragment = new CustomerPickerFragment();
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
        int orderId = CustomerPickerFragmentArgs.fromBundle(getArguments()).getOrderId();
        View view = inflater.inflate(R.layout.fragment_customer_picker, container, false);
        rvCustomerPicker = view.findViewById(R.id.rv_customer_picker);
        items = DatabaseHelper.findAllCustomer(view.getContext());
        adapter = new CustomerPickerAdapter(items, orderId);
        rvCustomerPicker.setAdapter(adapter);
        rvCustomerPicker.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
            if (item.get(DatabaseHelper.CL_HO_TEN).toLowerCase().contains(text.toLowerCase())
                    || item.get(DatabaseHelper.CL_SDT).toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filterList);
        }
    }
}