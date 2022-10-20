package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static int LOADING_TIME = 6000;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("userId",MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        if(!userName.equals("")){
            intent =  new Intent(this, Dashboard.class);
        }else
            intent = new Intent(this, LoginScreen.class);




        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, LOADING_TIME);
    }
}