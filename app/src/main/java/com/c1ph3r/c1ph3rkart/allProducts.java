package com.c1ph3r.c1ph3rkart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.DBHealper.ListOfProductsHelper;
import com.c1ph3r.c1ph3rkart.Model.ApplicationData;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.retroFitAPICall.Products;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Method to display all the products in the Data with filter options and search features.
public class allProducts extends Fragment implements productOnClick {
    // Declaring the required objects and views.
    BottomNavigationView bottomNav;
    ListOfProductsHelper listOfProducts;
    ImageButton cartButton;
    TextInputEditText search;
    TextInputLayout searchF;
    MaterialButtonToggleGroup priceFilter;
    RecyclerView productListViewer;
    ShimmerFrameLayout loading;
    Call<ApplicationData> call;
    Retrofit retrofit;
    Products products;
    View view;
    Dialog filterMenu;
    MaterialButton filterBtn, clearFilter;
    ArrayList<ProductList> productLists;

    // To change the bottom navigation.
    public allProducts(BottomNavigationView bottomNav) {
        this.bottomNav = bottomNav;
    }

    // Required empty public constructor
    public allProducts() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_products, container, false);

        if (view != null) {
            // Initializing the required Views.
            listOfProducts = new ListOfProductsHelper(requireActivity());
            productListViewer = view.findViewById(R.id.productListViewerD);
            cartButton = view.findViewById(R.id.cartButton);
            search = view.findViewById(R.id.searchFieldD);
            loading = view.findViewById(R.id.loadingScreenD);
            loading.startShimmer();
            searchF = view.findViewById(R.id.searchFieldLayoutD);
            filterBtn = view.findViewById(R.id.filterBtn);
            clearFilter = view.findViewById(R.id.clearFilters);

            // On click on cart : to forward cart page.
            cartButton.setOnClickListener(view1 -> {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dashboard, new checkoutCart()).commit();
                bottomNav.setSelectedItemId(R.id.cartDashBoard);
            });

            // API Object.
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dummyjson.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Interface the contains dynamic URLs.
            products = retrofit.create(Products.class);

            if (String.valueOf(search.getText()).isEmpty()) {
                call = products.getAllProducts();
                onListOfAllItems();
            }

            // Search option in the all products.
            searchF.setEndIconOnClickListener(view12 -> {
                loading.startShimmer();
                call = products.getSearchedProducts(String.valueOf(search.getText()));
                onListOfAllItems();
            });

        }

        // On click Filter btn : filters works based on price, categories, discount.
        filterBtn.setOnClickListener(view -> {
            try {
                filterMenuOptions();
                filterMenu.show();
                filterMenu.getWindow().getAttributes().windowAnimations = R.style.dialogAnimations;
                filterMenu.getWindow().setGravity(Gravity.BOTTOM);
            } catch (Exception e) {
                System.out.println("Exception in ALL PRODUCTS");
            }
        });
        return view;
    }

    // Works when the fragment is started.
    public void onStart() {
        super.onStart();


    }


    // Method to initialize the custom bottom menu contains filter options with various buttons
    @SuppressLint("NonConstantResourceId")
    private void filterMenuOptions() {
        filterMenu = new Dialog(getActivity());
        filterMenu.setContentView(R.layout.filter_layout);
        filterMenu.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(requireActivity(), R.drawable.filter_menu_background));
        filterMenu.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        MaterialButton apply = filterMenu.findViewById(R.id.applyBtn);
        MaterialButton cancel = filterMenu.findViewById(R.id.cancelFilterBtn);
        Chip ByDiscount = filterMenu.findViewById(R.id.byDiscount);
        priceFilter = filterMenu.findViewById(R.id.priceFilter);
        ChipGroup categories = filterMenu.findViewById(R.id.chipGroupFilter);
        ArrayList listOfCategories = listOfProducts.listOfCategories();
        // To assign the categories to the chip button.
        for (int i = 0; i < listOfCategories.size(); i++) {
            Chip value = new Chip(requireActivity());
            value.setText(String.valueOf(listOfCategories.get(i)));
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(requireActivity(),
                    null,
                    0,
                    R.style.CustomChip);
            value.setChipDrawable(chipDrawable);
            value.setId(i);
            categories.addView(value, i);
        }

        // OnClick Apply button to apply various filters.
        apply.setOnClickListener(view -> {
            List<Integer> selectedCategories = categories.getCheckedChipIds();
            productListViewer.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            loading.startShimmer();
            clearFilter.setVisibility(View.VISIBLE);

            // Filter products based on category process ( METHOD CALL)
            filterProducts(selectedCategories, listOfCategories);

            // Filter process based on the discount
            if (ByDiscount.isChecked()) {
                productLists.addAll(listOfProducts.filterProductsByDiscount());
            }

            // Filter products based on the price
            int value = priceFilter.getCheckedButtonId();
            switch (value) {
                case R.id.lowHigh:
                    // For low to high
                    productLists.addAll(listOfProducts.filterProductsByPrice("ASC"));
                    break;
                case R.id.highLow:
                    // For high to low
                    productLists.addAll(listOfProducts.filterProductsByPrice("DESC"));
                    break;
            }

            // If no filters is applied then refresh the productList.
            if (productLists.isEmpty())
                clearFilter.performClick();

            // After all of that filter the menu close.
            filterMenu.cancel();

        });

        // To cancel the filter menu.
        cancel.setOnClickListener(view -> {
            filterMenu.cancel();
        });

        // To clear all the filters.
        clearFilter.setOnClickListener(view -> {
            productListViewer.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            loading.startShimmer();
            call = products.getAllProducts();
            onListOfAllItems();
            clearFilter.setVisibility(View.GONE);
        });
    }


    // Method to Make the API call.
    public void onListOfAllItems() {
        // Enqueue : background process.
        call.enqueue(new Callback<ApplicationData>() {
            // If we receive any response this method is used to catch the response.
            @Override
            public void onResponse(@NonNull Call<ApplicationData> call, @NonNull Response<ApplicationData> response) {
                if (response.isSuccessful()) {
                    loading.stopShimmer();
                    loading.setVisibility(View.GONE);
                    System.out.println(search.getText());
                    productListViewer.setVisibility(View.VISIBLE);
                    assert response.body() != null;
                    productLists = new ArrayList<>(response.body().getProducts());
                    // converting the response body and setting the arraylist to the recycle view adapter.
                    setRecycleViewAdapter(productLists);
                } else {
                    // Error code will be displayed to the user.
                    System.out.println("Error to Load");
                    Toast.makeText(requireActivity(), "Error :" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            // If the API call failed.
            @Override
            public void onFailure(@NonNull Call<ApplicationData> call, @NonNull Throwable t) {
                System.out.println(t.getCause() + t.getLocalizedMessage());
            }
        });

    }

    // Setting the recycle view adapter with the array list.
    private void setRecycleViewAdapter(ArrayList<ProductList> productLists) {
        try {
            ProductListAdapter adapter = new ProductListAdapter(requireActivity(), productLists, allProducts.this);
            productListViewer.setAdapter(adapter);
            productListViewer.setLayoutManager(new LinearLayoutManager(getActivity()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // On click of the recycle view item: to display the particular product in a Activity.
    @Override
    public void onClickAnItem(int position) {
        Intent intent = new Intent(requireActivity(), SelectedItem.class);
        intent.putExtra("selectedProduct", productLists.get(position));
        intent.putExtra("From", "0");
        startActivity(intent);
    }


    void filterProducts(List<Integer> selectedCategories, ArrayList listOfCategories) {
        productLists = new ArrayList<>();
        for (int id : selectedCategories) {
            productLists.addAll(listOfProducts.filterProductsByCategory(String.valueOf(listOfCategories.get(id))));
        }
        loading.stopShimmer();
        loading.setVisibility(View.GONE);
        productListViewer.setVisibility(View.VISIBLE);
        setRecycleViewAdapter(productLists);
    }


}