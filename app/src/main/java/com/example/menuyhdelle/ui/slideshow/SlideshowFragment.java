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
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.R;

import java.io.File;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ArrayAdapter<Ingredient> adapter;
    String searchQry;
    MainClass main = MainClass.getMain();
    private File path;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        final ListView listView = root.findViewById(R.id.listview);

        // *** Clumsy way to store and load the ingredients as and example, ups here should be OSTOSLISTA ARRAY, not ingredient array, sorry..  ***
        // 1) context
        this.path = getContext().getApplicationContext().getFilesDir();

        // 2) add needed
        Ingredient apple = main.createIngredient("Omena", 1.0, "g", 1.1);
        Ingredient mincemeat = main.createIngredient("Jauheliha", 999.9, "kg", 0.1);
        Ingredient chickenbreast = main.createIngredient("Kanafile", 10.100, "10kg", 100.1);

        // 3) store when all done
        main.storeIngredients(this.path); // todo this is only for temporary location! onExitView or similar?

        // 4) load and use the arrray list
        ArrayList<Ingredient> ingredientArrayList = main.loadIngredients(this.path);

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

        main.storeIngredients(this.path); // todo this is only for temporary location! onExitView or similar?

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
