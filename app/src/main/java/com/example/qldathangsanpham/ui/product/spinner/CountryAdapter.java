package com.example.qldathangsanpham.ui.product.spinner;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qldathangsanpham.R;

import java.util.Arrays;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {
    private Context context;
    private List<Country> countryList;

    public CountryAdapter(@NonNull Context context) {
        super(context, 0);
        this.context = context;
        countryList = Arrays.asList(Country.values());
    }

    @Override
    public int getCount() {
        return countryList.size() + 1;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Nullable
    @Override
    public Country getItem(int position) {
        if (position == 0) {
            return null;
        }
        return Country.getCountryById(countryList.get(position - 1).getId());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_country_view, parent, false);
        } else {
            view = convertView;
        }
//        view = LayoutInflater.from(context).inflate(R.layout.activity_country_view, parent, false);

//        Country c = Country.getCountryById(countryList.get(position).getId());

        if (position > 0) {
            Country c = countryList.get(position - 1);

            TextView countryName = view.findViewById(R.id.countryName);
            TextView countryEmoji = view.findViewById(R.id.countryEmoji);

            countryName.setText(c.getName());
            countryEmoji.setText(c.getEmoji());
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if (position == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_country_header, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View root = parent.getRootView();
                    root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                    root.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                }
            });
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.activity_country_view, parent, false);

            TextView countryName = view.findViewById(R.id.countryName);
            TextView countryEmoji = view.findViewById(R.id.countryEmoji);

            Country c = countryList.get(position - 1);

            countryName.setText(c.getName());
            countryEmoji.setText(c.getEmoji());

        }

        return view;
    }
}