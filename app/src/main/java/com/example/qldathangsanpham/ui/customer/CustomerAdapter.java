package com.example.qldathangsanpham.ui.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends BaseAdapter implements Filterable {
    private Context context; //context
    private List<KhachHang> objects; //data source of the list adapter
    List<KhachHang> filteredObjects;
    private ItemFilter filter = new ItemFilter();

    public CustomerAdapter(Context context, List<KhachHang> items) {
        this.context = context;
        this.objects = items;
        this.filteredObjects = items;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setItems(List<KhachHang> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return filteredObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((KhachHang) getItem(position)).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_customer_view, parent, false);

        // get current item to be displayed
        KhachHang currentItem = (KhachHang) getItem(position);

        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.customerName);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.customerPhone);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getHoTen());
        textViewItemDescription.setText(currentItem.getSdt());

        // returns the view for the current row
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String search = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<KhachHang> list = objects;

            int count = list.size();
            final ArrayList<KhachHang> nlist = new ArrayList<KhachHang>(count);

            for (KhachHang k : objects) {
                if (k.getHoTen().toLowerCase().trim().contains(search) ||
                        k.getSdt().toLowerCase().trim().contains(search)) {
                    nlist.add(k);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredObjects = (ArrayList<KhachHang>) results.values;
            notifyDataSetChanged();
        }

    }
}

