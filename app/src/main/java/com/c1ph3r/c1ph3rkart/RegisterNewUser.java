package com.c1ph3r.c1ph3rkart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.c1ph3r.c1ph3rkart.Controller.AuthController;
import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterNewUser extends AppCompatActivity {
    TextInputEditText userName, password, confirmPassword, eMail, phoneNumber;
    TextInputLayout userNameLayout, passwordLayout, confirmPasswordLayout, eMailLayout, phoneNumberLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        userName = findViewById(R.id.userNameFieldR);
        password = findViewById(R.id.passwordFieldR);
        confirmPassword = findViewById(R.id.reEnterPasswordFieldR);
        eMail = findViewById(R.id.eMailFieldR);
        phoneNumber = findViewById(R.id.phoneNumberR);
    }

    public void onClickSubmitBtn(View view) {
        AuthController userRegister = new AuthController();
        UserDataBaseHelper userDB = new UserDataBaseHelper(this);
        int result = userRegister.userRegistration(String.valueOf(userName.getText()), String.valueOf(password.getText()), String.valueOf(confirmPassword.getText()), String.valueOf(eMail.getText()), String.valueOf(phoneNumber.getText()), userDB);
        switch (result) {
            case 1:
                Toast.makeText(this, "Enter a valid userName.", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Enter a valid password.", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Password does not match.", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Enter a valid phone number.", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Enter a valid Email Id.", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                break;
            case 7:
                Toast.makeText(this, "userName Already exists", Toast.LENGTH_SHORT).show();
                break;
            case 8:
                Toast.makeText(this, "EMail already exists", Toast.LENGTH_SHORT).show();
                break;
            case 9:
                Toast.makeText(this, "Phone Number already exists", Toast.LENGTH_SHORT).show();
                break;
            case 10:
                Toast.makeText(this, "Failed to register.", Toast.LENGTH_SHORT).show();
                break;
        }
        System.out.println("clicked");
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}