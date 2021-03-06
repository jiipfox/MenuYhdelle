package com.example.menuyhdelle.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
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
import android.widget.Toast;

import com.example.menuyhdelle.Dish;
import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.Menu;
import com.example.menuyhdelle.R;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.io.File;
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
    private File path;
    private Spinner spinner;
    private Button button, genButton;
    private ListView listView;
    private ArrayList<Dish> tempMenuList = new ArrayList<>();
    private Double cumSum = 0.0;
    private double dailySum = 0.0;
    MainClass main = MainClass.getMain();
    private Double co2Goal = 0.0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<Dish> adapter;
        ArrayList<Dish> tempMenuList;
        ArrayAdapter<Dish> itemsAdapter;
        PercentageChartView ringChart, ringChartWeekly, ringChartMonthly;

        this.path = getContext().getApplicationContext().getFilesDir();

        // Load ingredients database after login succeeded
        ArrayList<Ingredient> temp = main.loadIngredients(path);
        if (temp != null){
            if (temp.size() <= 1) {
                System.out.println("User has no ingredients, make some basic ingredients!");
            }
        }

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Load dishes and store in ArrayList to use in Spinner, watchout for null
        ArrayList<Dish> dishes = main.loadDishes(path);
        try {
            if (dishes == null){
                throw new Exception("Dish ArrayList is null, cannot set UI");
            }
            spinner = root.findViewById(R.id.foodSpinner);

            // Create ArrayAdapter for spinner and use previously loaded dishes, handle if dishes are null
            adapter = new ArrayAdapter<Dish>(getContext(), android.R.layout.simple_spinner_item, dishes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Menu list temp, add dishes to this and assign to user via main class method
            tempMenuList = new ArrayList<>();

            // Create adapter for listview to update based on selection + button.
            itemsAdapter = new ArrayAdapter<Dish>(getContext(), android.R.layout.simple_list_item_1, tempMenuList);
            listView = root.findViewById(R.id.mealsToday);
            listView.setAdapter(itemsAdapter);

        } catch (Exception e){
            System.out.println("Something went wrong while loading dishes");
            e.printStackTrace();
            return root;
        }

        ringChart = root.findViewById(R.id.view_daily);

        EditText eText = root.findViewById(R.id.pickDate);

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
                Float percentageOfTotal;
                Dish dish = null;
                co2Goal = main.getCurrentUserTargetCo2Value()/365; // kg or g?

                if (eText.getText().toString().isEmpty()){
                    makeToast("Valitse p??iv??!");
                }
                else {
                    // Get dish from the spinner menu and add to menu list
                    dish = (Dish) spinner.getSelectedItem();
                    tempMenuList.add(dish);

                    // Calculate daily sum and present percentage from the total
                    dailySum = dailySum + dish.getCO2();
                    percentageOfTotal = (float)((dailySum / co2Goal)*100.0);

                    // Notify user but let them eat
                    if (percentageOfTotal >= 100) {
                        makeToast("Ylitt??v?? ateria lis??tty: " + dish.getName() + " CO2: " + dish.getCO2() + "\n" + "T??n????n j??ljell??: " +
                                (co2Goal-dailySum));
                        percentageOfTotal = (float)100.0;
                    }else {
                        makeToast("Ateria lis??tty: " + dish.getName() + " CO2: " + dish.getCO2() + "\n" + "T??n????n j??ljell??: " +
                                (co2Goal-dailySum));
                    }

                    // Debugs
                    System.out.println("daily sum = " + dailySum);
                    System.out.println("daily goal = " + co2Goal);
                    System.out.println("%="+ percentageOfTotal);

                    // Update array adapter
                    itemsAdapter.notifyDataSetChanged();

                    try {
                        ringChart.setProgress(percentageOfTotal, true);
                    } catch (IllegalArgumentException e) {
                        makeToast("P??ivitt??inen p????st??m????r?? menisi yli t??m??n aterian j??lkeen.");
                    }
                }
            }
        });


        genButton = root.findViewById(R.id.genMenu);
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double co2 = 0.0;

                // Generate menu and get CO2 of each dish, save information on current user's JSON
                // for later use.
                try {
                    Date created = new SimpleDateFormat("dd/MM/yyyy").parse(eText.getText().toString());
                    Menu erikoisMenu = new Menu("Ruoka", tempMenuList, created);

                    System.out.println("Before: " + main.getCurrentUserCumulativeCo2());
                    co2 = erikoisMenu.getCO2();
                    System.out.println("Co2 of menu: " + co2);
                    main.assignMenuToCurrentUser(erikoisMenu);
                    System.out.println("After: " + main.getCurrentUserCumulativeCo2());

                    main.saveDb(path);

                    makeToast("Menu luotu ja tallennettu.");
                    itemsAdapter.clear();
                    ringChart.setProgress(0, true);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

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
                                co2Goal = main.getCurrentUserTargetCo2Value()/365;
                                dailySum = 0.0;
                                ringChart.setProgress(0, true);
                            }

                        }, year, month, day);
                picker.show();
                itemsAdapter.clear();
            }
        });
        return root;
    }

    public void makeToast(String text){
        Toast toast = Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
        toast.show();
    }

}