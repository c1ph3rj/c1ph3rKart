package com.c1ph3r.c1ph3rkart.DBHealper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.airbnb.lottie.L;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ListOfProductsHelper extends SQLiteOpenHelper {
Context context;

    public ListOfProductsHelper(@Nullable Context context ) {
        super(context, "Products", null , 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE productsDetails(id integer PRIMARY KEY AUTOINCREMENT, productName text, productCategory text, productPrice real, productDiscount real, productRatings real, productThumbnail text, productImages text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("productsDetails",null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name=?",new String[]{"productsDetails"});
        db.close();
    }

    public boolean addProducts(String productName, String productCategory, float productPrice, float productRatings, float productDiscount, String productThumbnail, String productImages){
        ListOfProductsHelper lb = new ListOfProductsHelper(context);
        SQLiteDatabase dB = lb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productName",productName);
        values.put("productCategory", productCategory);
        values.put("productPrice", productPrice);
        values.put("productRatings", productRatings);
        values.put("productDiscount", productDiscount);
        values.put("productThumbnail", productThumbnail);
        values.put("productImages", productImages);
        long insert = dB.insert("productsDetails",null,values);
        dB.close();
        return insert != -1;
    }

    public ArrayList filterProductsByCategory(String filter){
        ArrayList<ProductList> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor product = db.rawQuery("SELECT * FROM productsDetails WHERE productCategory=?",new String[]{filter});
        return addDataToArrayList(product, productList);
    }

    public ArrayList filterProductsByPrice(String order){
        ArrayList<ProductList> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor product = db.rawQuery("SELECT * FROM productsDetails ORDER BY productPrice " + order ,null);
        return addDataToArrayList(product, productList);
    }



    ArrayList addDataToArrayList(Cursor product, ArrayList<ProductList> productList){
        if(product.moveToFirst()){
            do{
                String productName = product.getString(1);
                String productCategory = product.getString(2);
                float productPrice = product.getFloat(3);
                float productDiscount = product.getFloat(4);
                float productRatings = product.getFloat(5);
                String productThumbnail = product.getString(6);
                ArrayList productImages = new ArrayList<>(Collections.singleton(product.getString(7)));
                productList.add(new ProductList(productName, productCategory, productPrice, productDiscount, productRatings, productThumbnail, productImages));
            }while(product.moveToNext());
        }
        for(ProductList products : productList){
            System.out.println(products.getTitle());
        }
        product.close();
        return productList;
    }
}
