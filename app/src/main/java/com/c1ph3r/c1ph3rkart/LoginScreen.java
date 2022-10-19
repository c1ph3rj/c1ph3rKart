package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginScreen extends AppCompatActivity {
    TextInputEditText userName, password;
    AuthController verifyUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        userName = findViewById(R.id.userNameField);
        password = findViewById(R.id.passwordField);
        verifyUser = new AuthController();

    }


    public void onClickLoginBtn(View view) {
        int result = verifyUser.userLoginVerification(String.valueOf(userName.getText()),String.valueOf(password.getText()));
        switch (result){
            case 1:
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onClickNewUser(View view) {
        Intent intent = new Intent(this, RegisterNewUser.class);
        startActivity(intent);
    }


}