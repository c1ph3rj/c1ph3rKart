package com.c1ph3r.c1ph3rkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.retroFitAPICall.Products;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListOfProducts extends AppCompatActivity implements productOnClick {
    RecyclerView productListViewer;
    TextInputEditText search;
    ShimmerFrameLayout loading;
    String value = "";
    Call<ApplicationData> call;
    ArrayList<ProductList> productLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        productListViewer = findViewById(R.id.productListViewerN);
        loading = findViewById(R.id.loadingScreen);
        loading.startShimmer();
        search = findViewById(R.id.searchFieldN);
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        productLists = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Products products = retrofit.create(Products.class);

        if(value !=null)
            call = products.getFilteredProducts(value);
        else
            call = products.getProducts();
        call.enqueue(new Callback<ApplicationData>() {
            @Override
            public void onResponse(@NonNull Call<ApplicationData> call, @NonNull Response<ApplicationData> response) {
                if(response.isSuccessful()){
                    loading.stopShimmer();
                    loading.setVisibility(View.GONE);
                    productListViewer.setVisibility(View.VISIBLE);
                    assert response.body() != null;
                    productLists = new ArrayList<>(response.body().getProducts());
                    setRecycleViewAdapter(productLists);
                }else {
                    System.out.println("Error to Load");
                    Toast.makeText(ListOfProducts.this, "Error :" + response.code(), Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onFailure(@NonNull Call<ApplicationData> call, @NonNull Throwable t) {
                System.out.println(t.getCause() + t.getLocalizedMessage());
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
        if(this.value!=null){
            intent.putExtra("value",this.value);
            intent.putExtra("selectedProduct", productLists.get(position));
        }else{
            intent.putExtra("selectedProduct", productLists.get(position));
        }
        startActivity(intent);
        finish();
    }



    public void onBackPressed(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}