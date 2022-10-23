package com.c1ph3r.c1ph3rkart.DBHealper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ListOfProductsHelper extends SQLiteOpenHelper {


    public ListOfProductsHelper(@Nullable Context context ) {
        super(context, "Products", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
