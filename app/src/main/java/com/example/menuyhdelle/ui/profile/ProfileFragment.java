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
            final EditText UserName = root.findViewById(R.id.userName);
            profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    UserName.setText(s);
                }
            });
            return root;
        }
    }

