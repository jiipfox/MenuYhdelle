package com.example.menuyhdelle.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.menuyhdelle.Dish;
import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.MainActivity;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.Menu;
import com.example.menuyhdelle.R;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.io.File;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Create weekly menu by selecting dishes for the appropriate sections and display how it scales to the co2 target.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayAdapter<Ingredient> adapter;
    ArrayAdapter<String> arrAdapter;
    MainClass main = MainClass.getMain();
    private File path;
    private Spinner spinner;
    private Button button, genButton;
    private ListView listView;
    private ArrayList<Dish> tempMenuList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            Double co2Target = ((MainActivity) getActivity()).getCurrentUserCo2Target();
            if (co2Target > 0.0){
                System.out.println("User target co2 in home view = " + co2Target);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot retrieve co2 target value from Home view, or Co2 is zero");
        }

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        this.path = getContext().getApplicationContext().getFilesDir();

        // Load dishes and store in ArrayList to use in Spinner

        ArrayList<Dish> dishes = main.loadDishes(path);

        spinner = root.findViewById(R.id.foodSpinner);

        // Create ArrayAdapter for spinner and use previously loaded dishes

        ArrayAdapter<Dish> adapter = new ArrayAdapter<Dish>(getContext(), android.R.layout.simple_spinner_item, dishes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Menu list temp, add dishes to this and assign to user via main class method

        ArrayList<Dish> tempMenuList = new ArrayList<>();

        // Create adapter for listview to update based on selection + button.

        ArrayAdapter<Dish> itemsAdapter = new ArrayAdapter<Dish>(getContext(), android.R.layout.simple_list_item_1, tempMenuList);
        ListView listView = root.findViewById(R.id.mealsToday);
        listView.setAdapter(itemsAdapter);

        PercentageChartView ringChart = root.findViewById(R.id.view_id);
        Double co2Goal = main.getCurrentUserTergetCo2Value();
        double cumSum = 0;

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Dish dish = (Dish) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button = root.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dish dish = (Dish) spinner.getSelectedItem();
                // Add dish / dishname to array and finally update listView adapter
                tempMenuList.add(dish);
                itemsAdapter.notifyDataSetChanged();
                double cumSum = 0f;
                for (Dish i : tempMenuList){
                    double temp = i.getCO2();
                    cumSum += temp;
                }
                double percentageOfTotal = cumSum/co2Goal;
                ringChart.setProgress((float) percentageOfTotal, true);
            }
        });


        genButton = root.findViewById(R.id.genMenu);
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICKED CLICK");
                Double co2 = 0.0;
                EditText eText = root.findViewById(R.id.pickDate);

                // Generate menu and get CO2 of each dish, save information on current user's JSON
                // for later use.

                try {
                    Date created = new SimpleDateFormat("dd/MM/yyyy").parse(eText.getText().toString());
                    for (int i = 0; i < tempMenuList.size(); i++){
                        System.out.println("("+i+") = " + tempMenuList.get(i).getName());
                        System.out.println("   co2 = " + tempMenuList.get(i).getCO2());
                    }
                    //created.setTime(1619270879*1000); // epoch time 24.4.21 in ms
                    Menu erikoisMenu = new Menu("Erikois", tempMenuList, created);
                    co2 = erikoisMenu.getCO2();
                    System.out.println(" Hiilidioksiidit käyttäjiiilllle = " + co2);
                    main.assignMenuToCurrentUser(erikoisMenu);
                    main.saveDb(path);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        EditText eText = root.findViewById(R.id.pickDate);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        return root;
    }

}
