package com.c1ph3r.c1ph3rkart.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductList implements Serializable {
    private float id;
    private String title;
    private String description;
    private float price;
    private float discountPercentage;
    private float rating;
    private float stock;
    private String brand;
    private String category;
    private String thumbnail;
    ArrayList<Object> images = new ArrayList<Object>();

    public ProductList(String productName, String productCategory, float productPrice, float productDiscount, float productRatings, String productThumbnail, ArrayList productImages) {
        this.title = productName;
        this.price = productPrice;
        this.discountPercentage = productDiscount;
        this.rating = productRatings;
        this.category = productCategory;
        this.thumbnail = productThumbnail;
        this.images = productImages;
    }


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public float getRating() {
        return rating;
    }

    public ArrayList<Object> getImages() {
        return images;
    }

    public float getStock() {
        return stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    // Setter Methods

    public void setId( float id ) {
        this.id = id;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setPrice( float price ) {
        this.price = price;
    }

    public void setDiscountPercentage( float discountPercentage ) {
        this.discountPercentage = discountPercentage;
    }

    public void setRating( float rating ) {
        this.rating = rating;
    }

    public void setStock( float stock ) {
        this.stock = stock;
    }

    public void setBrand( String brand ) {
        this.brand = brand;
    }

    public void setCategory( String category ) {
        this.category = category;
    }

    public void setThumbnail( String thumbnail ) {
        this.thumbnail = thumbnail;
    }
}
