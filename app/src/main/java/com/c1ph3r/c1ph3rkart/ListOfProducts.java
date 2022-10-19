package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;

public class ListOfProducts extends AppCompatActivity {
    RequestQueue requestQueue;
    RecyclerView productListViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard,new DashboardOptions()).commit();
//        requestQueue = Volley.newRequestQueue(this);
//        productListViewer = findViewById(R.id.productListViewer);
//        onListOfAllItems();
    }

//    public void onListOfAllItems(){
//        String url="https://dummyjson.com/Products";
//        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
//                response -> {
//                    try {
//                        Gson gson = new Gson();
//                        String output = String.valueOf(response);
//                        ApplicationData applicationData = gson.fromJson(output, ApplicationData.class);
////                      Glide.with(this).load(String.valueOf(applicationData.getProducts().get(1).getThumbnail())).into(image);
//                        ArrayList<ProductList> productLists = new ArrayList<>(applicationData.getProducts());
//                        ProductListAdapter adapter = new ProductListAdapter(this,productLists );
//                        productListViewer.setAdapter(adapter);
//                        productListViewer.setLayoutManager(new LinearLayoutManager(this));
//                        System.out.println(productLists.get(1).getBrand());;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }, error -> {
//
//        });
//        requestQueue.add(request);
//    }

    }

