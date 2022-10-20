package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.c1ph3r.c1ph3rkart.Adapter.ProductImagesAdapter;
import com.c1ph3r.c1ph3rkart.Model.ProductList;

import java.util.Objects;

public class SelectedItem extends AppCompatActivity {
    ProductList selectedItem;
    String value = "";
    TextView productName, brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);
        Intent intent = getIntent();
        productName = findViewById(R.id.selectedProductName);
        brandName = findViewById(R.id.selectedProductBrandName);
        selectedItem = (ProductList) intent.getSerializableExtra("selectedProduct");
        value = intent.getStringExtra("value");

        ViewPager productImages = findViewById(R.id.productImages);
        ProductImagesAdapter imageAdapter = new ProductImagesAdapter(this, selectedItem.getImages());
        productImages.setAdapter(imageAdapter);

        productName.setText(selectedItem.getTitle());
        brandName.setText(selectedItem.getBrand());


    }
    public void onBackPressed() {
        Intent intent = new Intent(this,ProductsInTheData.class);
        if(!Objects.equals(value, ""))
            intent.putExtra("value",value);
        startActivity(intent);
    }
}