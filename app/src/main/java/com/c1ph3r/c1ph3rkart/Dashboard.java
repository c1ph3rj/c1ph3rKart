package com.c1ph3r.c1ph3rkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Dashboard extends AppCompatActivity {
    public BottomNavigationView bottomNav;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        bottomNav = findViewById(R.id.bottomNavigation);
        Intent intent = getIntent();
        String value = intent.getStringExtra("DashBoard");
        System.out.println(value);
        Fragment fragment1 = null;
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, new DashboardOptions()).commit();
        if(value==null) {
            bottomNav.setSelectedItemId(R.id.dashboardOptions);
            fragment1 = new DashboardOptions();
        }else if(value.equals("2")) {
            bottomNav.setSelectedItemId(R.id.cartDashBoard);
            fragment1 = new checkoutCart();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, fragment1).commit();
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch(item.getItemId()){
                case R.id.dashboardOptions:
                    fragment = new DashboardOptions(bottomNav);
                    break;
                case R.id.allProducts:
                    fragment = new allProducts() ;
                    break;
                case R.id.cartDashBoard:
                    fragment = new checkoutCart();
                    break;
                case R.id.Settings:
                    fragment = new settings();
                    break;
            }
            if(fragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, fragment).commit();
            }
            return true;
        });
    }


    public void onBackPressed(){
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle("C1ph3R Kart!").setMessage("Do you Want to Logout?").setPositiveButton("No", (dialogInterface, i1) -> {}).setNegativeButton("yes", (dialogInterface, i1) -> {
            SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userName", "");
            editor.apply();
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomNav.getSelectedItemId();
    }

    protected void onResume() {
        super.onResume();
    }
}

