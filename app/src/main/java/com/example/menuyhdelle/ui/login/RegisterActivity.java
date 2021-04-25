package com.example.menuyhdelle.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.R;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button createUserBtn;
    TextView alrdyHaveUser;
    EditText editTextName, editTextUser, editTextPassword, editTextCO2;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainClass main = MainClass.getMain();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Find elements by their ID's.

        createUserBtn = findViewById(R.id.createUser);
        alrdyHaveUser = findViewById(R.id.alrdyHaveUser);
        editTextCO2 = findViewById(R.id.editTextCO2);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUser = findViewById(R.id.editTextUser);
        checkBox = findViewById(R.id.booleanValue);

        alrdyHaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change view to LoginActivity
                changeView(v);
            }
        });

        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextUser.getText().toString();
                String pass = editTextPassword.getText().toString(); // todo where to hash?
                double co2Obj = Double.parseDouble(editTextCO2.getText().toString());
                boolean bool = checkBox.isChecked();

                // Call abstraction, create new user and store the database
                if (main.createNewUser(name, pass, co2Obj)){
                    System.out.println("New user created.");
                    main.saveDb(getApplicationContext().getFilesDir());
                } else {
                    System.out.println("New user not created!");
                }

                // Save user to database and display toast "User successfully created" or some error
                // if any of the fields are left unfinished.
            }
        });
    }

    // This method will change view to LoginActivity if user clicks alrdyHaveUser button.

    public void changeView(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }
}