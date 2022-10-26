package com.c1ph3r.c1ph3rkart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.retroFitAPICall.Products;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class allProducts extends Fragment implements productOnClick {
    BottomNavigationView bottomNav;
    ImageButton cartButton;
    TextInputEditText search;
    TextInputLayout searchF;
    RecyclerView productListViewer;
    ShimmerFrameLayout loading;
    Call<ApplicationData> call;
    Retrofit retrofit;
    Products products;
    View view;
    ArrayList<ProductList> productLists;

    public allProducts(BottomNavigationView bottomNav) {
        this.bottomNav = bottomNav;
    }
    public allProducts() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_products, container, false);
    }

    public void onStart() {
        super.onStart();
        view = getView();
        if(view!= null){

            productListViewer = view.findViewById(R.id.productListViewerD);
            cartButton = view.findViewById(R.id.cartButton);
            search = view.findViewById(R.id.searchFieldD);
            loading = view.findViewById(R.id.loadingScreenD);
            loading.startShimmer();

            cartButton.setOnClickListener(view1 -> {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, new checkoutCart()).commit();
                bottomNav.setSelectedItemId(R.id.cartDashBoard);
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dummyjson.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            products = retrofit.create(Products.class);

            if(String.valueOf(search.getText()).isEmpty()){
                call = products.getAllProducts();
                onListOfAllItems();
            }

            searchF = view.findViewById(R.id.searchFieldLayoutD);
            searchF.setEndIconOnClickListener(view12 -> {
                loading.startShimmer();
                call = products.getSearchedProducts(String.valueOf(search.getText()));
                onListOfAllItems();
            });

        }

    }

    public void onListOfAllItems(){
        call.enqueue(new Callback<ApplicationData>() {
            @Override
            public void onResponse(@NonNull Call<ApplicationData> call, @NonNull Response<ApplicationData> response) {
                if(response.isSuccessful()){
                    loading.stopShimmer();
                    loading.setVisibility(View.GONE);
                    System.out.println(search.getText());
                    productListViewer.setVisibility(View.VISIBLE);
                    assert response.body() != null;
                    productLists = new ArrayList<>(response.body().getProducts());
                    setRecycleViewAdapter(productLists);
                }else {
                    System.out.println("Error to Load");
                    Toast.makeText(requireActivity(), "Error :" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApplicationData> call, @NonNull Throwable t) {
                System.out.println(t.getCause() + t.getLocalizedMessage());
            }
        });

    }

    private void setRecycleViewAdapter(ArrayList<ProductList> productLists) {
        try{
            ProductListAdapter adapter = new ProductListAdapter(requireActivity(),productLists, allProducts.this);
            productListViewer.setAdapter(adapter);
            productListViewer.setLayoutManager(new LinearLayoutManager(getActivity()));
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void onClickAnItem(int position) {
        Intent intent = new Intent(requireActivity(), SelectedItem.class);
        intent.putExtra("selectedProduct", productLists.get(position));
        intent.putExtra("From", "0");
        startActivity(intent);
    }


}