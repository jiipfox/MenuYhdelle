package com.example.menuyhdelle.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menuyhdelle.MainActivity;
import com.example.menuyhdelle.MainClass;
import com.example.menuyhdelle.R;

// Todo: Is this the first activity that is loaded?
public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView forgotPswd, createUser;
    EditText editTextUser, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MainClass main = MainClass.getMain();
        main.createNewUser("Teppo", "123456", 100.0);
        main.saveDb(getApplicationContext());
        main.loadDb(getApplicationContext());

        // Find elements by their ID's.
        loginBtn = findViewById(R.id.loginButton);
        forgotPswd = findViewById(R.id.forgotPsw);
        createUser = findViewById(R.id.createUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUser = findViewById(R.id.editTextUser);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change view to RegisterActivity
                changeView(v);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Login clicked");
                // Log in
                String text = "Kirjautuminen epäonnistui!";
                String name = editTextUser.getText().toString();
                String pass = editTextPassword.getText().toString();

                if (main.loginUser(name, pass)) {
                    //String name = main.getUserName();
                    System.out.println("Login succeeded, welcome " + name);
                    text = "Kirjautuminen onnistui!";
                } else{
                    System.out.println("Login NOT succeeeded.");
                }
                makeToast(text);
                loginView(v);
            }
        });

        forgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // How to get password back?
                String text = "Salasanan palautus pyydetty!";
                makeToast(text);
            }
        });
    }

    // This method will change view to RegisterActivity if user clicks createUser text.

    public void changeView(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, 1);
    }

    public void makeToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void loginView(View v) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(intent, 1);
    }
}