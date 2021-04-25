package com.example.menuyhdelle.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.menuyhdelle.R;

public class ProfileFragment extends Fragment {

        private ProfileViewModel profileViewModel;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            profileViewModel =
                    new ViewModelProvider(this).get(ProfileViewModel.class);
            View root = inflater.inflate(R.layout.fragment_profile, container, false);
            final EditText userName = root.findViewById(R.id.userName);
            final EditText co2Goal = root.findViewById(R.id.co2Goal);

            String uName = userName.getText().toString();
            String coGoal = co2Goal.getText().toString();

            // GET AND SET USER INFORMATION METHODS HERE BY CLICKING BUTTON

            return root;
        }
    }

