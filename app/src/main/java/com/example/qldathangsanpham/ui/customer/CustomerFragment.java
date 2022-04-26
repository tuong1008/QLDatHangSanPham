package com.example.qldathangsanpham.ui.customer;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.KhachHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomerFragment extends Fragment {
    DatabaseHelper angDoDatabaseHelper;
    public static CustomerAdapter customerAdapter;
    public static List<KhachHang> customersList;
    ListView lvCustomer;
    public static final String EXTRA_MAKH = "maKH";

    public static void setCustomersList(List<KhachHang> customersList) {
        CustomerFragment.customersList.clear();
        CustomerFragment.customersList.addAll(customersList);
        customerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        angDoDatabaseHelper = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        lvCustomer = view.findViewById(R.id.listCustomers);
        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CustomerFormActivity.class);
                i.putExtra(EXTRA_MAKH, -1);
                getActivity().startActivity(i);
            }
        });
        setCustomersListView();
        return view;
    }

    private void setCustomersListView() {
        try {
            customersList = angDoDatabaseHelper.getAllCustomers();
            customerAdapter = new CustomerAdapter(getActivity(), customersList);
            lvCustomer.setAdapter(customerAdapter);
            lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                    Intent intent = new Intent(getActivity(), CustomerFormActivity.class);
                    intent.putExtra(EXTRA_MAKH, (int) id);
                    startActivity(intent);
                }
            });


//            String tableName = "KhachHang";
//            Log.d("LogCursor", "getTableAsString called");
//            String tableString = String.format("Table %s:\n", tableName);
//            Cursor allRows  = customersCursor;
//            if (allRows.moveToFirst() ){
//                String[] columnNames = allRows.getColumnNames();
//                do {
//                    for (String name: columnNames) {
//                        tableString += String.format("%s: %s\n", name,
//                                allRows.getString(allRows.getColumnIndex(name)));
//                    }
//                    tableString += "\n";
//
//                } while (allRows.moveToNext());
//                Log.d("LogCursor", tableString);
//            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }
}