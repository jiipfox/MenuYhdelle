package com.example.menuyhdelle.ui.shoppinglist;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.Ingredient;
import com.example.menuyhdelle.R;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    ArrayAdapter<String> adapter;
    ArrayList<Ingredient> StringArrayList;
    String searchQry;
    ListView listView;
    Button addButton, delButton, expButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                new ViewModelProvider(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppinglist, container, false);


        findViewsById(root);

        ArrayList<String> StringArrayList = new ArrayList<>();
        StringArrayList.add("Omena");
        StringArrayList.add("Jauheliha");
        StringArrayList.add("Broileri");

        adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_multiple_choice, StringArrayList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        // Write item name to add

        EditText writeItem = root.findViewById(R.id.writeItemName);

        // Add item button for adding an item to list

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = writeItem.getText().toString();
                if (itemName.isEmpty()){
                    Toast toast = Toast.makeText(getContext(),"Kirjoita nimi",Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    StringArrayList.add(itemName);
                    adapter.notifyDataSetChanged();
                    writeItem.setText("");
                }
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
                    StringArrayList.remove(i);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        // Export data into CSV-file

        /*

        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] list = returnList(v);

                try {

                    BufferedWriter br = new BufferedWriter(new FileWriter(getContext().getFilesDir() + "myfile.csv"));
                    StringBuilder sb = new StringBuilder();

                    // Append strings from array
                    for (String element : list) {
                        sb.append(element);
                        sb.append(", \n");
                    }

                    br.write(sb.toString());
                    br.close();
                } catch (FileNotFoundException ex){
                    System.out.println(ex.toString());
                } catch (IOException ex){
                    System.out.println(ex.toString());
                }
            }
        });

         */

        return root;

    }

    private void findViewsById(View v) {
        listView = v.findViewById(R.id.listView);
        //expButton = v.findViewById(R.id.exportBtn);
        delButton = v.findViewById(R.id.emptyBtn);
        addButton = v.findViewById(R.id.addItemBtn);
    }

    // Returns list of selected items to either export or delete from listview

    public String[] returnList(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
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
