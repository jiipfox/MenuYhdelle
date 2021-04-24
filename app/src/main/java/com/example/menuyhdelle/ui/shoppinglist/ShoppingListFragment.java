package com.example.menuyhdelle.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.menuyhdelle.Dish;
import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.IngredientAdapter;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.Menu;
import com.example.menuyhdelle.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    ArrayAdapter<Ingredient> adapter;
    String searchQry;
    MainClass main = MainClass.getMain();
    private File path;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

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

        // 4) load and use the array list
        ArrayList<Ingredient> ingredientArrayList = main.loadIngredients(this.path);

        // Create the adapter to convert the array to views
        IngredientAdapter adapter = new IngredientAdapter(this.getContext(), ingredientArrayList);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);


        /* ****TEST DISHES ***** */
        // load ingredient´s
        ArrayList<Ingredient> ingredientArrayList2 = main.loadIngredients(path);
        // create dish
        main.createDish("Kaktus-sieni-kikkare", ingredientArrayList2,"Kasvis", "Ruoka??", "keitä ja valmista");

        // test with some variations
        main.createIngredient("Vesi", 0.3, "dl", 9.9);
        main.storeIngredients(path);

        ingredientArrayList = main.loadIngredients(path);
        main.createDish("Parempi-keitto", ingredientArrayList,"Kasvis", "Juoma??", "kaada ja nauti");
        main.storeDishes(path);
        ArrayList<Dish> dishes = main.loadDishes(path);

        // Clear button for clearing shopping list

        Button clearBtn = root.findViewById(R.id.emptyBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
            }
        });

        /** Menu tests: */
        Date created = new Date();
        created.setTime(1619270879*1000); // epoch time 24.4.21 in ms
        Menu erikoisMenu = new Menu("Erikois", dishes, created);
        main.assignMenuToCurrentUser(erikoisMenu);

        ArrayList<Menu> testMenuLists = main.retrieveMenuOfCurrentUser();

        System.out.println("HERE COMES MENU PRINT: ");
        for (int i = 0; i< testMenuLists.size();i++){
            System.out.println(testMenuLists.get(i).getName());
        }

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
