package com.c1ph3r.c1ph3rkart.retroFitAPICall;


import com.c1ph3r.c1ph3rkart.Model.ApplicationData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Products {

    @GET("/products?limit=100")
    Call<ApplicationData> getProducts();
}
