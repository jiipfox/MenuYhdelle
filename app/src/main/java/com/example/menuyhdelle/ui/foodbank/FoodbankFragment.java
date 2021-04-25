package com.example.menuyhdelle.ui.foodbank;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.Dish;
import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.R;
import com.example.menuyhdelle.ui.shoppinglist.ShoppingListFragment;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class FoodbankFragment extends Fragment {

    private FoodbankViewModel foodbankViewModel;
    private File path;
    Dish dish;
    Spinner spinner;
    MainClass main = MainClass.getMain();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        foodbankViewModel =
                new ViewModelProvider(this).get(FoodbankViewModel.class);
        View root = inflater.inflate(R.layout.fragment_foodbank, container, false);
        final TextView textView = root.findViewById(R.id.foodItemName);
        final TextView foodItemList = root.findViewById(R.id.foodItemList);
        this.path = getContext().getApplicationContext().getFilesDir();

        ArrayList<Dish> dishes = main.loadDishes(path);

        spinner = root.findViewById(R.id.foodSpinner);

        // Create ArrayAdapter for spinner and use previously loaded dishes

        ArrayAdapter<Dish> adapter = new ArrayAdapter<Dish>(getContext(), android.R.layout.simple_spinner_item, dishes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dish = (Dish) parent.getSelectedItem();
                textView.setText(dish.getName());
                foodItemList.setText("");
                ArrayList<Ingredient> ingredients = dish.getIngredients();
                for (Ingredient i : ingredients) {
                    foodItemList.append(i.getName()+"\n");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button addBtn = root.findViewById(R.id.addToListBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }
}