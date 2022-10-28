package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.c1ph3r.c1ph3rkart.Model.AddressDetails;
import com.c1ph3r.c1ph3rkart.Model.userDetail;

public class ConfirmOrder extends AppCompatActivity {
    int AddressId;
    String userName;
    userDetail user;
    UserDataBaseHelper userDB;
    AddressDetails userAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Intent intent = getIntent();
        AddressId = intent.getIntExtra("AddressIndex", 0);


        SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","");

        userDB = new UserDataBaseHelper(this);
        user = userDB.getUserData(userName);
        userAddress = userDB.getAddress(userName).get(AddressId);



    }
}