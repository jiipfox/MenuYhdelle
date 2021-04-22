package com.example.menuyhdelle.ui.foodbank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.R;
import com.example.menuyhdelle.ui.slideshow.SlideshowViewModel;

public class FoodbankFragment extends Fragment {

    private FoodbankViewModel foodbankViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        foodbankViewModel =
                new ViewModelProvider(this).get(FoodbankViewModel.class);
        View root = inflater.inflate(R.layout.fragment_foodbank, container, false);
        final TextView textView = root.findViewById(R.id.foodItemName);
        final TextView foodItemList = root.findViewById(R.id.foodItemList);
        foodbankViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Härkiswokki");
                foodItemList.setText("Punasipuli 2\n" +
                        "Porkkana 3\n" +
                        "Valkosipulinkynsi 3\n" +
                        "Vihreä pakaste papu 200 g\n" +
                        "Seesaminsiemen 50 g\n" +
                        "Täysjyvänuudeli 200 g\n" +
                        "Rypsiöljy 2 rkl\n" +
                        "Lime 1\n" +
                        "Punainen currytahna 2 rkl\n" +
                        "Beanit -härkäpapusuikale 250 g\n" +
                        "Soijakastike 2 rkl");
            }
        });
        return root;
    }
}