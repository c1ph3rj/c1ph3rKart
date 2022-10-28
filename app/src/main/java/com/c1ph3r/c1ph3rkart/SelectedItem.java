package com.c1ph3r.c1ph3rkart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.c1ph3r.c1ph3rkart.Adapter.ProductImagesAdapter;
import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.Model.userDetail;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.Timer;
import java.util.TimerTask;

public class SelectedItem extends AppCompatActivity {
    ProductList selectedItem;
    String value = "", From = "";
    TextView productName, brandName, description, discount, price, priceAfterDiscount, ratings;
    ViewPager productImages;
    TabLayout tabIndicator;
    Runnable runnable;
    Timer timer;
    JsonArray Cart;
    Dialog dialog;
    Handler sliderHandler = new Handler();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        productName = findViewById(R.id.selectedProductName);
        brandName = findViewById(R.id.selectedProductBrandName);
        description = findViewById(R.id.selectedProductDescription);
        discount = findViewById(R.id.selectedProductDiscount);
        price = findViewById(R.id.selectedProductPrice);
        priceAfterDiscount = findViewById(R.id.selectedPrice);
        ratings = findViewById(R.id.selectedProductRatings);
        tabIndicator = findViewById(R.id.tabIndicator);

        Intent intent = getIntent();
        selectedItem = (ProductList) intent.getSerializableExtra("selectedProduct");
        value = intent.getStringExtra("value");
        From = intent.getStringExtra("From");

        productImages = findViewById(R.id.productImages);
        ProductImagesAdapter imageAdapter = new ProductImagesAdapter(this, selectedItem.getImages());
        productImages.setAdapter(imageAdapter);
        tabIndicator.setupWithViewPager(productImages, true);


        productName.setText(selectedItem.getTitle());
        brandName.setText(selectedItem.getBrand());
        ratings.setText(String.valueOf(selectedItem.getRating()));
        description.setText(description.getText() + selectedItem.getDescription());
        discount.setText(discount.getText() + String.valueOf(selectedItem.getDiscountPercentage()) + "%");
        price.setText("$ " + price.getText() + selectedItem.getPrice());
        price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double discountPrice = (selectedItem.getDiscountPercentage() / 100) * selectedItem.getPrice();
        priceAfterDiscount.setText("$ " + Math.ceil(selectedItem.getPrice() - discountPrice) + " only !  ");


        runnable = () -> {
            int position = productImages.getCurrentItem();
            System.out.println(position);
            if (position == selectedItem.getImages().size() - 1) {
                System.out.println(position + "in statement.");
                position = 0;
                productImages.setCurrentItem(position);
            } else
                productImages.setCurrentItem(position + 1, true);

        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sliderHandler.post(runnable);
            }
        }, 3000, 3000);
    }

    @SuppressLint("SetTextI18n")
    void customDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_to_cart_confrim);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.image_slider_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        MaterialButton addToCart = dialog.findViewById(R.id.addToCartBtn);
        MaterialButton cancel = dialog.findViewById(R.id.cancelBtn);
        TextView productName = dialog.findViewById(R.id.productNameCart);
        Cart = new JsonArray();
        SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        productName.setText(productName.getText() + selectedItem.getTitle());


        addToCart.setOnClickListener(view -> {
            if (userName.equals("")) {
                localCart();
            } else {
                SQLiteCart(userName, Cart.toString(), " ");
            }
            Toast.makeText(this, "Successfully added to the cart.", Toast.LENGTH_SHORT).show();
            dialog.cancel();

        });

        cancel.setOnClickListener(view -> {
            dialog.cancel();
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        });

    }

    private void SQLiteCart(String userName, String cart, String orderDetails) {
        UserDataBaseHelper userDB = new UserDataBaseHelper(this);
        userDetail user = userDB.getUserData(userName);
        String Value = String.valueOf(Math.round(selectedItem.getId()));
        try {
            if (!user.getCart().isEmpty()) {
                Cart = (JsonArray) JsonParser.parseString(user.getCart());
                Cart.add(Value);
            }
        } catch (Exception e) {
            System.out.println(e);
            Cart.add(Value);
        }
        userDB.updateUserData(userName, Cart.toString(), orderDetails);
    }


    public void onClickAddToCartInSelectedItems(View view) {
        customDialog();
        dialog.show();
    }

    void localCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("Cart", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("Cart", "none").equals("none")) {
            Cart = JsonParser.parseString(sharedPreferences.getString("Cart", "none")).getAsJsonArray();
            String Value = String.valueOf(Math.round(selectedItem.getId()));
            Cart.add(Value);
        } else {
            String Value = String.valueOf(Math.round(selectedItem.getId()));
            Cart.add(Value);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Cart", Cart.toString());
        System.out.println(Cart.toString());
        editor.apply();
    }


    public void onClickCartBtn(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("DashBoard", "2");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
    }
}