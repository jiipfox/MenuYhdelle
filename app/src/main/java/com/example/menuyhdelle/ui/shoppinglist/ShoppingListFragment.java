package com.example.menuyhdelle.ui.shoppinglist;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

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
    //ArrayAdapter<Ingredient> adapter;
    ArrayAdapter<String> spinAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<String> StringArrayList;
    MainClass main = MainClass.getMain();
    private File path;
    Button delButton, addBtn;
    ListView listView;
    Spinner ingSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);

        findViewsById(root);

        // *** Clumsy way to store and load the ingredients as and example, ups here should be OSTOSLISTA ARRAY, not ingredient array, sorry..  ***
        // 1) context
        this.path = getContext().getApplicationContext().getFilesDir();

        // 2) add needed
        //Ingredient apple = main.createIngredient("Omena", 1.0, "g", 1.1);
        //Ingredient mincemeat = main.createIngredient("Jauheliha", 15.0, "kg", 0.1);
        //Ingredient chickenbreast = main.createIngredient("Kanafile", 100.1, "10kg", 100.1);

        // 3) store when all done
        //main.storeIngredients(this.path); // todo this is only for temporary location! onExitView or similar?

        // 4) load and use the array list
        ArrayList<Ingredient> ingredientArrayList = main.loadIngredients(this.path);
        ArrayList<String> StringArrayList = new ArrayList<>();
        for (Ingredient i : ingredientArrayList){
            StringArrayList.add(i.getName());
        }
        // Temp list for listview
        ArrayList<String> tempList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, tempList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);


        // Add ingredients to dropdown
        spinAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, StringArrayList);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ingSpinner.setAdapter(spinAdapter);
        ingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ing = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Delete selected items from list
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] list = returnList(v);

                if (list.length==0){
                    Toast toast = Toast.makeText(getContext(),"Ei poistettavaa",Toast.LENGTH_SHORT);
                    toast.show();
                }

                for(String i : list){
                    tempList.remove(i);
                    adapter.notifyDataSetChanged();
                }
                listView.clearChoices();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = ingSpinner.getSelectedItem().toString();
                tempList.add(temp);
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    private void findViewsById(View v) {
        listView = v.findViewById(R.id.listview);
        delButton = v.findViewById(R.id.emptyBtn);
        addBtn = v.findViewById(R.id.addButton);
        ingSpinner = v.findViewById(R.id.ingSpinner);
    }

    // Returns list of selected items to either delete from listview

    public String[] returnList(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add item if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
        return outputStrArr;
    }
}

