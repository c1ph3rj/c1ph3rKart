package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.c1ph3r.c1ph3rkart.Adapter.ProductImagesAdapter;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SelectedItem extends AppCompatActivity {
    ProductList selectedItem;
    String value = "",From = "";
    TextView productName, brandName,description, discount, price, priceAfterDiscount, ratings;
    ViewPager productImages;
    TabLayout tabIndicator;
    Runnable runnable;
    Timer timer;
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
        price.setText("$ "+ price.getText()+ String.valueOf(selectedItem.getPrice()));
        price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double discountPrice = (selectedItem.getDiscountPercentage() / 100 ) * selectedItem.getPrice();
        priceAfterDiscount.setText( "$ "+String.valueOf(Math.ceil(selectedItem.getPrice() - discountPrice)) + " only !  ");


        runnable = () -> {
            int position = productImages.getCurrentItem();
            System.out.println(position);
            if(position == selectedItem.getImages().size()-1) {
                System.out.println(position + "in statement.");
                position = 0;
                productImages.setCurrentItem(position);
            }else
                productImages.setCurrentItem(position + 1, true);

        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sliderHandler.post(runnable);
            }
        }, 3000,3000);
    }


}