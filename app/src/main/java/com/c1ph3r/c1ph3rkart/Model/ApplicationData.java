package com.c1ph3r.c1ph3rkart.Model;

import java.util.ArrayList;

public class ApplicationData {
    ArrayList<ProductList> products = new ArrayList<>();
    private float total;
    private float skip;
    private float limit;


    // Getter Methods

    public float getTotal() {
        return total;
    }

    public float getSkip() {
        return skip;
    }

    public float getLimit() {
        return limit;
    }

    public ArrayList<ProductList> getProducts() {
        return products;
    }
}
