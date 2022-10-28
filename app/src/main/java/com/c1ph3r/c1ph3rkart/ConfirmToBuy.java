package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.c1ph3r.c1ph3rkart.Adapter.AddressListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.c1ph3r.c1ph3rkart.Model.AddressDetails;
import com.c1ph3r.c1ph3rkart.Model.userDetail;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ConfirmToBuy extends AppCompatActivity implements productOnClick {
    MaterialButton cancel, addNewAddress;
    RecyclerView addressListView;
    userDetail user;
    UserDataBaseHelper userDB;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_to_buy);
        cancel = findViewById(R.id.cancelAdd);
        addNewAddress = findViewById( R.id.addAddressToUserDB);
        addressListView = findViewById(R.id.userAddress);

        SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","");


        userDB = new UserDataBaseHelper(this);
        userDB.createAddressTable(userName);
        user = userDB.getUserData(userName);

       try {
           ArrayList<AddressDetails> userAddress = userDB.getAddress(userName);
           AddressListAdapter addressAdapter = new AddressListAdapter(this, userAddress,this);
           addressListView.setAdapter(addressAdapter);
           addressListView.setLayoutManager(new LinearLayoutManager(this));
       }catch (Exception e){
           Toast.makeText(this, "Add Address To continue.", Toast.LENGTH_SHORT).show();
       }
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmToBuy.this, AddAddress.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClickAnItem(int position) {
        Intent intent = new Intent(ConfirmToBuy.this, ConfirmOrder.class);
        intent.putExtra("AddressIndex", position);
        System.out.println(position);
        startActivity(intent);
    }
}