package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.c1ph3r.c1ph3rkart.Adapter.ProductImagesAdapter;
import com.c1ph3r.c1ph3rkart.Model.ProductList;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SelectedItem extends AppCompatActivity {
    ProductList selectedItem;
    String value = "";
    TextView productName, brandName,description, discount, price;
    ViewPager productImages;
    Handler sliderHandler = new Handler();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        Intent intent = getIntent();
        productName = findViewById(R.id.selectedProductName);
        brandName = findViewById(R.id.selectedProductBrandName);
        description = findViewById(R.id.selectedProductDescription);
        discount = findViewById(R.id.selectedProductDiscount);
        price = findViewById(R.id.selectedProductPrice);

        selectedItem = (ProductList) intent.getSerializableExtra("selectedProduct");
        value = intent.getStringExtra("value");

        productImages = findViewById(R.id.productImages);
        ProductImagesAdapter imageAdapter = new ProductImagesAdapter(this, selectedItem.getImages());
        productImages.setAdapter(imageAdapter);


        productName.setText(productName.getText() + ": " + selectedItem.getTitle());
        brandName.setText(brandName.getText() +": " +selectedItem.getBrand());
        description.setText(description.getText() + selectedItem.getDescription());
        discount.setText(discount.getText() + String.valueOf(selectedItem.getDiscountPercentage()) + "%");
        price.setText(price.getText()+ String.valueOf(selectedItem.getPrice()));


        Runnable runnable = () -> {
            int position = productImages.getCurrentItem();
            System.out.println(position);
            if(position == selectedItem.getImages().size()-1) {
                System.out.println(position + "in statement.");
                position = 0;
                productImages.setCurrentItem(position);
            }else
                productImages.setCurrentItem(position + 1, true);

        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sliderHandler.post(runnable);
            }
        }, 3000,3000);
    }




    public void onBackPressed() {
        Intent intent = new Intent(this,ProductsInTheData.class);
        if(!Objects.equals(value, ""))
            intent.putExtra("value",value);
        startActivity(intent);
    }
}