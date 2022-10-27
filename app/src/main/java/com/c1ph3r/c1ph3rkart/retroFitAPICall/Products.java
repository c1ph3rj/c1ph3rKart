package com.c1ph3r.c1ph3rkart.retroFitAPICall;


import com.c1ph3r.c1ph3rkart.Model.ApplicationData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Products {

    // To get the Given 30 products in the API.
    @GET("/products")
    Call<ApplicationData> getProducts();

    // To get all the products in the API.
    @GET("/products?limit=100")
    Call<ApplicationData> getAllProducts();

    // To search the particular product in the API data.
    @GET("/products/search")
    Call<ApplicationData> getSearchedProducts(@Query("q") String value);

    // To get the products based on their category.
    @GET("/products/category/{category}")
    Call<ApplicationData> getFilteredProducts(@Path("category") String category);
}
