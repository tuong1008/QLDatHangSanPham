package com.example.qldathangsanpham.ui.customer;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CustomerChartFragment extends Fragment {
    private SQLiteDatabase db;
    private static final String SET_LABEL = "Số Lượng";
    private List<String> alphabets = new ArrayList<>();

    private BarChart chart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_chart, container, false);

        chart = view.findViewById(R.id.fragment_verticalbarchart_chart);

        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);

        return view;
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

    private BarData createChartData() {
        SQLiteOpenHelper angDoDatabaseHelper = new DatabaseHelper(getActivity());
        db = angDoDatabaseHelper.getReadableDatabase();
        TreeMap<String, Integer> thongKeTenKH = ((DatabaseHelper) angDoDatabaseHelper).getThongKeTenKH(db);

        ArrayList<BarEntry> values = new ArrayList<>();

        int i = 0;
        for (Map.Entry<String, Integer>
                entry : thongKeTenKH.entrySet()) {
            alphabets.add(entry.getKey());
            float x = i;
            float y = entry.getValue();
            values.add(new BarEntry(x, y));
            i++;
        }

        BarDataSet set1 = new BarDataSet(values, SET_LABEL);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        return data;
    }

    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(alphabets.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return alphabets.get((int) value);
            }
        });

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);
    }
}