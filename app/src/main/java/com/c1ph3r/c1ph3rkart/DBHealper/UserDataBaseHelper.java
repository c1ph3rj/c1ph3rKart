package com.c1ph3r.c1ph3rkart.DBHealper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDataBaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase userDBRead;

    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "userDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE userDetails(Id integer PRIMARY KEY AUTOINCREMENT, userName text, password text, eMail text, phoneNumber text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createAddressTable(String userName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE " + userName + "_Address (Id integer PRIMARY KEY AUTOINCREMENT, name text, houseNo text, streetName text, city text, state text, pinCode text, addressType text)");

    }

    public boolean isUserNameInTheTable(String userName) {
        userDBRead = this.getReadableDatabase();
        Cursor userNameMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE userName =?", new String[]{String.valueOf(userName)});
        int val = userNameMatch.getCount();
        userNameMatch.close();
        return (val == 0);
    }

    public boolean isEMailInTheTable(String eMail) {
        userDBRead = this.getReadableDatabase();
        Cursor eMailMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE eMail =?", new String[]{String.valueOf(eMail)});
        int val = eMailMatch.getCount();
        eMailMatch.close();
        return (val == 0);
    }

    public boolean isPhoneNumberInTheTable(String phoneNumber) {
        userDBRead = this.getReadableDatabase();
        Cursor eMailMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE PhoneNumber =?", new String[]{String.valueOf(phoneNumber)});
        int val = eMailMatch.getCount();
        eMailMatch.close();
        return (val == 0);
    }

    public int verifyTheUser(String userName, String password) {
        userDBRead = this.getReadableDatabase();
        Cursor userNameMatch = userDBRead.rawQuery("SELECT userName FROM userDetails WHERE userName =?", new String[]{userName});
        Cursor passwordMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE userName =? AND password =?", new String[]{userName, password});
        userNameMatch.moveToFirst();
        if (userNameMatch.getCount() == 1 && userNameMatch.getString(0).equals(userName)) {
            passwordMatch.moveToFirst();
            if (passwordMatch.getCount() == 1 && ((passwordMatch.getString(1).equals(userName)) && passwordMatch.getString(2).equals(password))) {
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public boolean addUserToUserDetails(String userName, String password, String eMail, String phoneNumber) {
        SQLiteDatabase userDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("password", password);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("eMail", eMail);
        long insert = userDataBase.insert("userDetails", null, contentValues);
        // returns false if the data does not add on the sqlite DB.
        return insert != -1;
    }
}
