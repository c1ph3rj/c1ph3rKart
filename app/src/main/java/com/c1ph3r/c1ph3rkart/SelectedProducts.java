package com.c1ph3r.c1ph3rkart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class SelectedProducts extends Fragment {
    RequestQueue requestQueue;
    RecyclerView productListViewer;
    public SelectedProducts() {
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
        return inflater.inflate(R.layout.fragment_selected_products, container, false);
    }

    public void onStart() {
        super.onStart();

        requestQueue = Volley.newRequestQueue(requireActivity());
        onListOfAllItems();
    }

    public void onListOfAllItems(){
        View view = getView();
        String url="https://dummyjson.com/Products";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        productListViewer = view.findViewById(R.id.productListViewer);
                        Gson gson = new Gson();
                        String output = String.valueOf(response);
                        ApplicationData applicationData = gson.fromJson(output, ApplicationData.class);
                        ArrayList<ProductList> productLists = new ArrayList<>(applicationData.getProducts());
                        ProductListAdapter adapter = new ProductListAdapter(getActivity(),productLists );
                        productListViewer.setAdapter(adapter);
                        productListViewer.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        System.out.println(productLists.get(1).getBrand());;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });
        requestQueue.add(request);
    }

}