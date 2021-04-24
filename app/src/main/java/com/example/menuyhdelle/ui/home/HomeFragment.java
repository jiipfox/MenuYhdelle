package com.example.menuyhdelle.ui.home;

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

import com.example.menuyhdelle.MainActivity;
import com.example.menuyhdelle.R;

/**
 * Create weekly menu by selecting dishes for the appropriate sections and display how it scales to the co2 target.
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

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

        return root;
    }
}