package com.c1ph3r.c1ph3rkart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.DBHealper.ListOfProductsHelper;
import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.c1ph3r.c1ph3rkart.Model.AddressDetails;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.Model.userDetail;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class ConfirmOrder extends AppCompatActivity implements productOnClick {
    int AddressId;
    String userName;
    userDetail user;
    MaterialButton confirm, cancel;
    UserDataBaseHelper userDB;
    AddressDetails userAddress;
    TextView price, address, discountPrice;
    RecyclerView orderList;
    ListOfProductsHelper listOfProductsHelper;
    ArrayList<ProductList> productsInOrder;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Intent intent = getIntent();
        AddressId = intent.getIntExtra("AddressIndex", 0);
        confirm = findViewById(R.id.confirmOrder);
        cancel = findViewById(R.id.cancelOrder);


        SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        userDB = new UserDataBaseHelper(this);
        user = userDB.getUserData(userName);
        userAddress = userDB.getAddress(userName).get(AddressId);

        orderList = findViewById(R.id.orderConfirmList);
        price = findViewById(R.id.price);
        address = findViewById(R.id.addressConfirm);
        discountPrice = findViewById(R.id.discountPrice);

        listOfProductsHelper = new ListOfProductsHelper(this);
        productsInOrder = listOfProductsHelper.getProductByID(JsonParser.parseString(user.getCart()).getAsJsonArray());
        ProductListAdapter adapter = new ProductListAdapter(this, productsInOrder, this);
        orderList.setAdapter(adapter);
        orderList.setLayoutManager(new LinearLayoutManager(this));

        String add = userAddress.getName() + "\nAddress:\n" + userAddress.getHouseNo() + ",\n" + userAddress.getStreetName() + ",\n" + userAddress.getState() + ",\n" + userAddress.getPinCode() + ".";
        address.setText(add);
        float totalPrice = 0;
        double discountP = 0;
        for (ProductList product : productsInOrder) {
            double discountPrice = (product.getDiscountPercentage() / 100) * product.getPrice();
            discountP = (discountP + product.getPrice() - discountPrice);
            totalPrice = totalPrice + product.getPrice();
        }
        price.setText("$ " + (Math.round(totalPrice)));
        price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        discountPrice.setText("$ " + (Math.round(discountP)));


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(ConfirmOrder.this);
                alertDialogBuilder.setTitle("C1ph3R Kart!").setMessage("Order Placed Successfully!").setPositiveButton("Ok", (dialogInterface, i1) -> {
                    userDB.updateUserData(userName, " ", " ");
                    Intent intent = new Intent(ConfirmOrder.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                });
                alertDialogBuilder.show();
            }
        });


    }

    @Override
    public void onClickAnItem(int position) {
        Toast.makeText(this, productsInOrder.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, checkoutCart.class);
    }
}