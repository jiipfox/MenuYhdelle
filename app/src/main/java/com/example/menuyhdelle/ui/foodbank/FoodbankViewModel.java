package com.example.menuyhdelle.ui.foodbank;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodbankViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoodbankViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ruokapankki");
    }

    public LiveData<String> getText() {
        return mText;
    }
}