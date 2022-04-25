package com.example.qldathangsanpham.ui.order.oderhome;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.qldathangsanpham.ui.order.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    private OrderHomeEvents orderHomeEvents;
    private FloatingActionButton fabCreateOrder;
    private RecyclerView rvOrder;
    private OrderAdapter adapter;
    private Context context;
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Map<String, String>> items;

    public OrderHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderHomeFragment newInstance(String param1, String param2) {
        OrderHomeFragment fragment = new OrderHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttach(@NonNull Activity activity) {
//        super.onAttach(activity);
//        // the callback interface. If not, it throws an exception
//        try {
//            orderHomeEvents = (OrderHomeFragment.OrderHomeEvents) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement TextClicked");
//        }
//    }

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
        // Inflate the layout for this fragment
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_order_home, container, false);
        rvOrder = view.findViewById(R.id.rv_order);
        items = DatabaseHelper.findAllOrder(view.getContext());
        adapter = new OrderAdapter(items);
        rvOrder.setAdapter(adapter);
        rvOrder.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvOrder.setHasFixedSize(true);
        enableSwipeToDeleteAndUndo();
        fabCreateOrder = view.findViewById(R.id.fab_add_oder);
        fabCreateOrder.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(OrderHomeFragmentDirections.actionPickCustomer());
        });
        return view;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Map<String, String> item = adapter.getItems().get(position);

                adapter.removeItem(position);

                Snackbar snackbar = Snackbar.make(view, "Đã xóa đơn hàng.", Snackbar.LENGTH_LONG);
                snackbar.setAction("HOÀN TÁC", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.restoreItem(item, position);
                        rvOrder.scrollToPosition(position);
                    }
                });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        adapter.removeFromDatabase(context, position);
                    }
                });

                snackbar.setActionTextColor(Color.CYAN);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvOrder);
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
            if (item.get(DatabaseHelper.CL_ID).toLowerCase().contains(text.toLowerCase())
                    || item.get(DatabaseHelper.CL_HO_TEN).toLowerCase().contains(text.toLowerCase())) {
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