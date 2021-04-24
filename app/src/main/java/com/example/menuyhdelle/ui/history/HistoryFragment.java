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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.Dish;
import com.example.menuyhdelle.Menu;
import com.example.menuyhdelle.ListViewAdapter;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HistoryFragment extends Fragment {
    
    MainClass main = MainClass.getMain();
    private HistoryViewModel historyViewModel;
    private ArrayList<Menu> menuList;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        menuList = main.retrieveMenuOfCurrentUser();

        ListView lview = root.findViewById(R.id.listview);
        ListViewAdapter adapter = new ListViewAdapter(this.getActivity(), menuList);
        lview.setAdapter(adapter);

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
                                +"Nimi: " + item +"\n"
                                +"CO2-arvo: " + co2, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    public void makeChart(View view, ArrayList<Menu> data){
    }

}