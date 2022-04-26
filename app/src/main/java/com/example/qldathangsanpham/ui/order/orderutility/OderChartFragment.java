package com.example.qldathangsanpham.ui.order.orderutility;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OderChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OderChartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private Context context;

    public OderChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OderChart.
     */
    // TODO: Rename and change types and number of parameters
    public static OderChartFragment newInstance(String param1, String param2) {
        OderChartFragment fragment = new OderChartFragment();
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
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_oder_chart, container, false);
        PieChart orderChart = view.findViewById(R.id.pc_order_pie_chart);
        List<Map<String, String>> datas = DatabaseHelper.getDataForOderChart(view.getContext());
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map<String, String> data :
                datas) {
            System.out.println(data.get("SLDDH"));
            System.out.println(data.get(DatabaseHelper.CL_HO_TEN));
            entries.add(new PieEntry(Integer.parseInt(data.get("SLDDH")), "(" + data.get(DatabaseHelper.CL_ID) + ") " + data.get(DatabaseHelper.CL_HO_TEN)));
        }
        PieDataSet pieDataSet = new PieDataSet(entries, "Biểu đồ số lượng đơn hàng của khách hàng");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(R.color.white);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        orderChart.setData(pieData);
        orderChart.getDescription().setEnabled(false);
        orderChart.animate();
        return view;
    }


}