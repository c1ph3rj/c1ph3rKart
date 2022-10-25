package com.c1ph3r.c1ph3rkart.retroFitAPICall;


import com.c1ph3r.c1ph3rkart.Model.ApplicationData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Products {

    @GET("/products/category/fragrances")
    Call<ApplicationData> getProducts();

    @GET("/products/category/{category}")
    Call<ApplicationData> getFilteredProducts(@Path("category") String category);
}
