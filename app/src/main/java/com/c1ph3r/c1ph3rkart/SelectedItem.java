package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.c1ph3r.c1ph3rkart.Adapter.ProductImagesAdapter;
import com.c1ph3r.c1ph3rkart.Model.ProductList;

import java.util.Objects;

public class SelectedItem extends AppCompatActivity {
    ProductList selectedItem;
    String value = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        Intent intent = getIntent();
        selectedItem = (ProductList) intent.getSerializableExtra("selectedProduct");
        value = intent.getStringExtra("value");

        ViewPager productImages = findViewById(R.id.productImages);
        ProductImagesAdapter imageAdapter = new ProductImagesAdapter(this, selectedItem.getImages());
        productImages.setAdapter(imageAdapter);
    }
    public void onBackPressed() {
        Intent intent = new Intent(this,ProductsInTheData.class);
        if(!Objects.equals(value, ""))
            intent.putExtra("value",value);
        startActivity(intent);
    }
}