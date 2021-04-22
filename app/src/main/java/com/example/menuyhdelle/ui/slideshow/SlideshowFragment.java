package com.example.menuyhdelle.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.DishIngredient;
import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.IngredientAdapter;
import com.example.menuyhdelle.R;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ArrayAdapter<Ingredient> adapter;
    String searchQry;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        final ListView listView = root.findViewById(R.id.listview);

        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        Ingredient apple = new Ingredient("Omena");
        Ingredient mincemeat = new Ingredient("Jauheliha");
        Ingredient chickenbreast = new Ingredient("Kanafile");
        ingredientArrayList.add(apple);
        ingredientArrayList.add(mincemeat);
        ingredientArrayList.add(chickenbreast);

        // Create the adapter to convert the array to views
        IngredientAdapter adapter = new IngredientAdapter(this.getContext(), ingredientArrayList);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        // Clear button for clearing shopping list

        Button clearBtn = root.findViewById(R.id.emptyBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
            }
        });

        // Export button to export files in CSV

        Button exportBtn = root.findViewById(R.id.exportBtn);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Export method here
            }
        });

        // Search items

        return root;
    }


}
