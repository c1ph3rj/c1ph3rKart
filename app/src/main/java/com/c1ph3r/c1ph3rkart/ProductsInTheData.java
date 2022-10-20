package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class ProductsInTheData extends AppCompatActivity implements productOnClick {
    RequestQueue requestQueue;
    RecyclerView productListViewer;
    TextInputEditText search;
    String value = "";
    ArrayList<ProductList> productLists, filteredProducts, searchedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_in_the_data);
        productListViewer = findViewById(R.id.productListViewer);
        requestQueue = Volley.newRequestQueue(this);
        search = findViewById(R.id.searchField);
        Intent intent = getIntent();
        value = intent.getStringExtra("value");
        onListOfAllItems();
        search.setText(null);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = charSequence.toString().toLowerCase();
                searchedProducts = new ArrayList<>();
                if(filteredProducts!=null){
                    for (int j = 0;j<filteredProducts.size();j++){
                        if(filteredProducts.get(j).getBrand().toLowerCase().contains(value))
                            searchedProducts.add(filteredProducts.get(j));
                        else if(filteredProducts.get(j).getTitle().toLowerCase().contains(value))
                            searchedProducts.add(filteredProducts.get(j));
                    }
                    setRecycleViewAdapter(searchedProducts);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onListOfAllItems(){
        String url="https://dummyjson.com/Products";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        System.out.println(value);
                        Gson gson = new Gson();
                        String output = String.valueOf(response);
                        ApplicationData applicationData = gson.fromJson(output, ApplicationData.class);
                        productLists = new ArrayList<>(applicationData.getProducts());
                        filteredProducts = new ArrayList<>();
                        if(value!=null){
                            for (int i = 0;i<productLists.size();i++){
                                if(productLists.get(i).getCategory().equals(value))
                                    filteredProducts.add(productLists.get(i));
                            }
                            setRecycleViewAdapter(filteredProducts);
                        }else
                            setRecycleViewAdapter(productLists);
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

    @Override
    public void onClickAnItem(int position) {
        Intent intent = new Intent(this, SelectedItem.class);
        System.out.println(this.value);
        if(this.value!=null){
            intent.putExtra("value",this.value);
            intent.putExtra("selectedProduct", filteredProducts.get(position));
        }else{
            intent.putExtra("selectedProduct", productLists.get(position));
        }
        startActivity(intent);
        finish();
    }

    void setRecycleViewAdapter(ArrayList<ProductList> value){
        ProductListAdapter adapter = new ProductListAdapter(this,value,this );
        productListViewer.setAdapter(adapter);
        productListViewer.setLayoutManager(new LinearLayoutManager(this));
    }
}