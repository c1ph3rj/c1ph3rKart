package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.retroFitAPICall.Products;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListOfProducts extends AppCompatActivity implements productOnClick {
    RecyclerView productListViewer;
    TextInputEditText search;
    String value = "";
    ArrayList<ProductList> productLists, filteredProducts, searchedProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        productListViewer = findViewById(R.id.productListViewerN);
        search = findViewById(R.id.searchFieldN);
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        productLists = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Products products = retrofit.create(Products.class);


        Call<ApplicationData> call = products.getProducts();
        call.enqueue(new Callback<ApplicationData>() {
            @Override
            public void onResponse(Call<ApplicationData> call, Response<ApplicationData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ListOfProducts.this, "Failed : " + response.code(), Toast.LENGTH_SHORT).show();
                }
                assert response.body() != null;
                productLists = new ArrayList<>(response.body().getProducts());
                setRecycleViewAdapter(productLists);


            }

            @Override
            public void onFailure(Call<ApplicationData> call, Throwable t) {
                System.out.println(t.getCause() + t.getLocalizedMessage() + t.toString());
            }
        });


    }


    void setRecycleViewAdapter(ArrayList<ProductList> value){
        ProductListAdapter adapter = new ProductListAdapter(this,value,this );
        productListViewer.setAdapter(adapter);
        productListViewer.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickAnItem(int position) {
        Intent intent = new Intent(this, SelectedItem.class);
        System.out.println(this.value);
          intent.putExtra("selectedProduct", productLists.get(position));

        startActivity(intent);
        finish();
    }
}