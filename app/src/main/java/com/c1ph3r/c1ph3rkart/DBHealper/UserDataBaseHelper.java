package com.c1ph3r.c1ph3rkart.DBHealper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataBaseHelper extends SQLiteOpenHelper {
    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "userDataBase", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE userDetails(Id integer PRIMARY KEY AUTOINCREMENT, userName text, password text, eMail text, phoneNumber text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
