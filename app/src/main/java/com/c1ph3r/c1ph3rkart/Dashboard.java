package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, new DashboardOptions()).commit();

    }

    public void onBackPressed(){
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle("C1ph3R Kart!").setMessage("Do you Want to Logout?").setPositiveButton("No", (dialogInterface, i1) -> {}).setNegativeButton("yes", (dialogInterface, i1) -> {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }).show();
    }
}

