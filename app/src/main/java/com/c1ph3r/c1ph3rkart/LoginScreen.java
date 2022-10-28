package com.c1ph3r.c1ph3rkart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.c1ph3r.c1ph3rkart.Controller.AuthController;
import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginScreen extends AppCompatActivity {
    TextInputEditText userName, password;
    AuthController verifyUser;
    UserDataBaseHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        userName = findViewById(R.id.userNameField);
        password = findViewById(R.id.passwordField);
        verifyUser = new AuthController();

    }


    public void onClickLoginBtn(View view) {
        userDB = new UserDataBaseHelper(this);
        int result = verifyUser.userLoginVerification(String.valueOf(userName.getText()), String.valueOf(password.getText()), userDB);
        switch (result) {
            case 1:
                Toast.makeText(this, "userName or password Incorrect", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Password incorrect", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                SharedPreferences Cart = getSharedPreferences("Cart", Context.MODE_PRIVATE);
                if (!Cart.getString("Cart", "none").equals("none")) {
                    userDB.updateUserData(String.valueOf(userName.getText()), Cart.getString("Cart", "none"), "");
                    SharedPreferences.Editor editCart = Cart.edit();
                    editCart.clear();
                    editCart.apply();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", String.valueOf(userName.getText()));
                editor.apply();
                Toast.makeText(this, "Welcome " + userName.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickNewUser(View view) {
        Intent intent = new Intent(this, RegisterNewUser.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

}