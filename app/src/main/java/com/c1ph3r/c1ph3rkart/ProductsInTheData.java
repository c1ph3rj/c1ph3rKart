package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ProductsInTheData extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView productListViewer;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_in_the_data);
        productListViewer = findViewById(R.id.productListViewer);
        requestQueue = Volley.newRequestQueue(this);
        onListOfAllItems();
    }

    public void onListOfAllItems(){
        String url="https://dummyjson.com/Products";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Intent intent = getIntent();
                        value = intent.getStringExtra("value");
                        System.out.println(value);
                        Gson gson = new Gson();
                        String output = String.valueOf(response);
                        ApplicationData applicationData = gson.fromJson(output, ApplicationData.class);
                        ArrayList<ProductList> productLists = new ArrayList<>(applicationData.getProducts());
                        ArrayList<ProductList> filteredProducts = new ArrayList<>();
                        for (int i = 0;i<productLists.size();i++){
                            if(productLists.get(i).getCategory().equals(value))
                                filteredProducts.add(productLists.get(i));
                        }
                        ProductListAdapter adapter = new ProductListAdapter(this,filteredProducts );
                        productListViewer.setAdapter(adapter);
                        productListViewer.setLayoutManager(new LinearLayoutManager(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });
        requestQueue.add(request);
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}