package com.example.menuyhdelle.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ProfileFragment extends Fragment {

        private ProfileViewModel profileViewModel;
        MainClass main = MainClass.getMain();

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            profileViewModel =
                    new ViewModelProvider(this).get(ProfileViewModel.class);
            View root = inflater.inflate(R.layout.fragment_profile, container, false);
            final EditText userName = root.findViewById(R.id.userName);
            final EditText co2Goal = root.findViewById(R.id.co2Goal);

            userName.setText(main.getUserFullName());
            co2Goal.setText(String.valueOf(main.getCo2Goal()));
            boolean preferLowCo = main.getCo2Prefer();

            return root;
        }
    }

