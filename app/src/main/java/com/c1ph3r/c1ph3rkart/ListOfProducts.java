package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListOfProducts extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView ProductName, rating, price;
    ImageView thumbNailImage;
    RecyclerView productListViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        requestQueue = Volley.newRequestQueue(this);
        productListViewer = findViewById(R.id.productListViewer);
        onListOfAllItems();
    }

    public void onListOfAllItems(){
        String url="https://dummyjson.com/Products";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        String output = String.valueOf(response);
                        ApplicationData applicationData = gson.fromJson(output, ApplicationData.class);
//                      Glide.with(this).load(String.valueOf(applicationData.getProducts().get(1).getThumbnail())).into(image);
                        ArrayList<ProductList> productLists = new ArrayList<>(applicationData.getProducts());
                        ProductListAdapter adapter = new ProductListAdapter(this,productLists );
                        productListViewer.setAdapter(adapter);
                        productListViewer.setLayoutManager(new LinearLayoutManager(this));
                        System.out.println(productLists.get(1).getBrand());;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });
        requestQueue.add(request);
    }

    }

