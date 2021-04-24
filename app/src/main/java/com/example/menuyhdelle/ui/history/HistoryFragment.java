package com.example.menuyhdelle.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.ListViewAdapter;
import com.example.menuyhdelle.Model;
import com.example.menuyhdelle.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private ArrayList<Model> productList;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);


        productList = new ArrayList<Model>();
        ListView lview = root.findViewById(R.id.listview);
        ListViewAdapter adapter = new ListViewAdapter(this.getActivity(), productList);
        lview.setAdapter(adapter);

        // Populate list (for table)

        populateList();
        // For the charts

        LineChart lineChart;
        LineData lineData;
        List<Entry> entryList = new ArrayList<>();
        lineChart = root.findViewById(R.id.lineChart);

        entryList.add(new Entry(1,20));
        entryList.add(new Entry(2,10));
        entryList.add(new Entry(3,31));
        entryList.add(new Entry(4,14));

        LineDataSet lineDataSet = new LineDataSet(entryList,"country");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setFillAlpha(110);
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(10);
        lineChart.invalidate();
        // Update listview adapter as the items have been created within the list

        adapter.notifyDataSetChanged();

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String sDate = ((TextView)view.findViewById(R.id.date)).getText().toString();
                String item = ((TextView)view.findViewById(R.id.item)).getText().toString();
                String co2 = ((TextView)view.findViewById(R.id.co2)).getText().toString();

                Toast.makeText(root.getContext(),
                        "Päivämäärä: " + sDate +"\n"
                                +"Tuote: " + item +"\n"
                                +"CO2-arvo: " + co2, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void populateList() {

        Model item1, item2, item3, item4, item5;

        item1 = new Model("24.4.2021", "Omenapiirakka", "2.0");
        productList.add(item1);

        item2 = new Model("23.4.2021", "Jauheliha-makaronilaatikko", "4.2");
        productList.add(item2);

        item3 = new Model("23.4.2021", "Soijabolognese", "1.4");
        productList.add(item3);

        item4 = new Model("21.4.2021", "Belugaborssi", "0.5");
        productList.add(item4);

        item5 = new Model("20.4.2021", "Pastavuoka", "1.25");
        productList.add(item5);
    }
}