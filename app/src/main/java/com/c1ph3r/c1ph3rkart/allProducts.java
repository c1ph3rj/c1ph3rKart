package com.c1ph3r.c1ph3rkart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class allProducts extends Fragment implements productOnClick {
    BottomNavigationView bottomNav;
    ImageButton cartButton;
    TextInputEditText search;
    RequestQueue requestQueue;
    RecyclerView productListViewer;
    ArrayList<ProductList> productLists, filteredProducts;

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
        View view = getView();
        if(view!= null){
            productListViewer = view.findViewById(R.id.productListViewerD);
            requestQueue = Volley.newRequestQueue(requireActivity());
            cartButton = view.findViewById(R.id.cartButton);
            search = view.findViewById(R.id.searchFieldD);
            cartButton.setOnClickListener(view1 -> {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, new checkoutCart()).commit();
                bottomNav.setSelectedItemId(R.id.cartU);
            });
            onListOfAllItems();

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String value = charSequence.toString().toLowerCase();
                    filteredProducts = new ArrayList<>();
                    for (int j = 0;j<productLists.size();j++){
                        if(productLists.get(j).getBrand().toLowerCase().contains(value))
                            filteredProducts.add(productLists.get(j));
                        else if(productLists.get(j).getTitle().toLowerCase().contains(value))
                            filteredProducts.add(productLists.get(j));
                    }
                    ProductListAdapter adapter = new ProductListAdapter(requireActivity(),filteredProducts, allProducts.this);
                    productListViewer.setAdapter(adapter);
                    productListViewer.setLayoutManager(new LinearLayoutManager(getActivity()));

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }

    public void onListOfAllItems(){
        String url="https://dummyjson.com/Products";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        String output = String.valueOf(response);
                        ApplicationData applicationData = gson.fromJson(output, ApplicationData.class);
                        productLists = new ArrayList<>(applicationData.getProducts());
                        ProductListAdapter adapter = new ProductListAdapter(requireActivity(),productLists, allProducts.this);
                        productListViewer.setAdapter(adapter);
                        productListViewer.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });
        requestQueue.add(request);
    }

    @Override
    public void onClickAnItem(int position) {
        Intent intent = new Intent(requireActivity(), SelectedItem.class);
        if(filteredProducts!=null)
            intent.putExtra("selectedProduct", filteredProducts.get(position));
        else
            intent.putExtra("selectedProduct", productLists.get(position));
        startActivity(intent);
        requireActivity().finish();
    }


}