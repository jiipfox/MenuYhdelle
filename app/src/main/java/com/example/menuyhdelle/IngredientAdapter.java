package com.example.menuyhdelle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {


    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredientArrayList) {
        super(context, 0, ingredientArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ingredient ingredient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }
        // Lookup view for data population
        TextView itName = (TextView) convertView.findViewById(R.id.item_name);
        // Populate the data into the template view using the data object
        itName.setText(ingredient.getName());
        // Return the completed view to render on screen
        return convertView;
    }

    // Filter class to filter results with SearchView
}

