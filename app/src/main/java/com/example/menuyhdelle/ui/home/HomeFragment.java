package com.example.menuyhdelle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.Dish;
import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.MainActivity;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.Menu;
import com.example.menuyhdelle.R;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

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
                System.out.println(tempMenuList);
            }
        });
        genButton = root.findViewById(R.id.genMenu);
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date created = new Date();
                System.out.println(tempMenuList);
                created.setTime(1619270879*1000); // epoch time 24.4.21 in ms
                Menu erikoisMenu = new Menu("Erikois", tempMenuList, created);
                main.assignMenuToCurrentUser(erikoisMenu);
            }
        });
        return root;
    }

}
