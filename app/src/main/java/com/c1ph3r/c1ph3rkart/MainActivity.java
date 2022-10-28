package com.c1ph3r.c1ph3rkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.c1ph3r.c1ph3rkart.DBHealper.ListOfProductsHelper;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.retroFitAPICall.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*
* TODO
*   create a filter layout
*   add to cart features
*   proceed to buy feature
*   address
*   store address accordingly
*   filter : discount
*   profile management
* */



public class MainActivity extends AppCompatActivity {
    private static int LOADING_TIME = 6000;
    Intent intent;
    ArrayList<ProductList> productLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences sharedPreferences = getSharedPreferences("userId",MODE_PRIVATE);
//        String userName = sharedPreferences.getString("userName","");

        intent =  new Intent(this, Dashboard.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Products products = retrofit.create(Products.class);
        Call call = products.getAllProducts();
        ListOfProductsHelper db = new ListOfProductsHelper(this);

        call.enqueue(new Callback<ApplicationData>() {
            @Override
            public void onResponse(@NonNull Call<ApplicationData> call, @NonNull Response<ApplicationData> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    productLists = new ArrayList<>(response.body().getProducts());
                    db.deleteTable();
                    addProductsToDB();
                }else {
                    System.out.println("Error to Load");
                    Toast.makeText(MainActivity.this, "Error :" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApplicationData> call, @NonNull Throwable t) {
                System.out.println(t.getCause() + t.getLocalizedMessage());
            }
        });


        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, LOADING_TIME);
    }


    void addProductsToDB() {
       ListOfProductsHelper productsDB = new ListOfProductsHelper(this);
        for(ProductList product : productLists){
            productsDB.addProducts(product.getTitle(), product.getCategory(), product.getPrice(), product.getRating(), product.getDiscountPercentage(), product.getThumbnail(), product.getImages().toString(), product.getBrand(), product.getDescription());
        }
        //productsDB.filterProductsByCategory("smartphones");
        //productsDB.filterProductsByPrice("DESC");
    }
}