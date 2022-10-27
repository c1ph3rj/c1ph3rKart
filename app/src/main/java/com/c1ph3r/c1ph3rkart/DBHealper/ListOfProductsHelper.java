package com.c1ph3r.c1ph3rkart.DBHealper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.c1ph3r.c1ph3rkart.Model.ProductList;

import java.util.ArrayList;
import java.util.Collections;

public class ListOfProductsHelper extends SQLiteOpenHelper {
    Context context;

    // To create DB (PRODUCTS)
    public ListOfProductsHelper(@Nullable Context context) {
        super(context, "Products", null, 1);
        this.context = context;
    }

    // To create Table (PRODUCT DETAILS)
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE productsDetails(id integer PRIMARY KEY AUTOINCREMENT, productName text, productCategory text, productPrice real, productDiscount real, productRatings real, productThumbnail text, productImages text, productBrand text, productDescription text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // To delete Table.
    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("productsDetails", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name=?", new String[]{"productsDetails"});
        db.close();
    }

    // To add products to the Table.
    public boolean addProducts(String productName, String productCategory, float productPrice, float productRatings, float productDiscount, String productThumbnail, String productImages, String productBrand, String productDescription) {
        ListOfProductsHelper lb = new ListOfProductsHelper(context);
        SQLiteDatabase dB = lb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productName", productName);
        values.put("productCategory", productCategory);
        values.put("productPrice", productPrice);
        values.put("productRatings", productRatings);
        values.put("productDiscount", productDiscount);
        values.put("productThumbnail", productThumbnail);
        values.put("productImages", productImages);
        values.put("productBrand", productBrand);
        values.put("productDescription", productDescription);
        long insert = dB.insert("productsDetails", null, values);
        dB.close();
        return insert != -1;
    }

    // To filter the products by the category
    public ArrayList<ProductList> filterProductsByCategory(String filter) {
        ArrayList<ProductList> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor product = db.rawQuery("SELECT * FROM productsDetails WHERE productCategory=?", new String[]{filter});
        return addDataToArrayList(product, productList);
    }

    // To filter products by the discount
    public ArrayList filterProductsByDiscount() {
        ArrayList<ProductList> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor product = db.rawQuery("SELECT * FROM productsDetails ORDER BY productDiscount DESC", null);
        return addDataToArrayList(product, productList);
    }

    // To filter products Based on the price both Ascending and Descending order
    public ArrayList filterProductsByPrice(String order) {
        ArrayList<ProductList> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor product = db.rawQuery("SELECT * FROM productsDetails ORDER BY productPrice " + order, null);
        return addDataToArrayList(product, productList);
    }

    // To get the List of categories for filter.
    public ArrayList listOfCategories() {
        ArrayList<String> listOfCategories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor category = db.rawQuery("SELECT productCategory FROM productsDetails", null);
        category.moveToFirst();
        do {
            if (listOfCategories.isEmpty())
                listOfCategories.add(category.getString(0));
            else {
                if (!listOfCategories.get(listOfCategories.size() - 1).equals(category.getString(0))) {
                    listOfCategories.add(category.getString(0));
                }
            }

        } while (category.moveToNext());
        category.close();
        return listOfCategories;
    }

    // Method to add the data to the model class
    ArrayList addDataToArrayList(Cursor product, ArrayList<ProductList> productList) {
        if (product.moveToFirst()) {
            do {
                String productName = product.getString(1);
                String productCategory = product.getString(2);
                float productPrice = product.getFloat(3);
                float productDiscount = product.getFloat(4);
                float productRatings = product.getFloat(5);
                String productThumbnail = product.getString(6);
                String productBrand = product.getString(8);
                String productDescription = product.getString(9);
                ArrayList productImages = new ArrayList<>(Collections.singleton(product.getString(7)));
                productList.add(new ProductList(productName, productCategory, productPrice, productDiscount, productRatings, productThumbnail, productImages, productBrand, productDescription));
            } while (product.moveToNext());
        }
        product.close();
        return productList;
    }

}
