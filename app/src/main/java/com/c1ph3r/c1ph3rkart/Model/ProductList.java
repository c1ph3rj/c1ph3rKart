package com.c1ph3r.c1ph3rkart.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductList implements Serializable {
    ArrayList<Object> images = new ArrayList<>();
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


    // Getter Methods

    public ProductList(float productId, String productName, String productCategory, float productPrice, float productDiscount, float productRatings, String productThumbnail, ArrayList productImages, String productBrand, String productDescription) {
        this.id = productId;
        this.title = productName;
        this.price = productPrice;
        this.discountPercentage = productDiscount;
        this.rating = productRatings;
        this.category = productCategory;
        this.thumbnail = productThumbnail;
        this.images = productImages;
        this.brand = productBrand;
        this.description = productDescription;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    // Setter Methods

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<Object> getImages() {
        return images;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
