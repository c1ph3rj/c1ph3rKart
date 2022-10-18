package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

    }


    public void onClickLoginBtn(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}