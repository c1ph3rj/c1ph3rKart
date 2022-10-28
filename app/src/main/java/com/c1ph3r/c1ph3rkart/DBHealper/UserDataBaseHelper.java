package com.c1ph3r.c1ph3rkart.DBHealper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.c1ph3r.c1ph3rkart.Model.AddressDetails;
import com.c1ph3r.c1ph3rkart.Model.userDetail;

import java.util.ArrayList;

public class UserDataBaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase userDBRead;

    // To create DB (userDataBase)
    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "userDataBase", null, 1);
    }

    // To create Table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE userDetails(Id integer PRIMARY KEY AUTOINCREMENT, userName text, password text, eMail text, phoneNumber text, cart text, orderDetails text)");
    }

    // To update table if already exists
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // To create table to store the Address of the user
    public void createAddressTable(String userName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + userName + "_Address (Id integer PRIMARY KEY AUTOINCREMENT, name text, houseNo text, streetName text, state text, pinCode text, phoneNumber text)");

    }

    // To verify the userName already present in the table
    public boolean isUserNameInTheTable(String userName) {
        userDBRead = this.getReadableDatabase();
        Cursor userNameMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE userName =?", new String[]{String.valueOf(userName)});
        int val = userNameMatch.getCount();
        userNameMatch.close();
        return (val == 0);
    }

    // To verify the Email Id already present in the table
    public boolean isEMailInTheTable(String eMail) {
        userDBRead = this.getReadableDatabase();
        Cursor eMailMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE eMail =?", new String[]{String.valueOf(eMail)});
        int val = eMailMatch.getCount();
        eMailMatch.close();
        return (val == 0);
    }

    // To verify the phone number already present in the table
    public boolean isPhoneNumberInTheTable(String phoneNumber) {
        userDBRead = this.getReadableDatabase();
        Cursor eMailMatch = userDBRead.rawQuery("SELECT * FROM userDetails WHERE PhoneNumber =?", new String[]{String.valueOf(phoneNumber)});
        int val = eMailMatch.getCount();
        eMailMatch.close();
        return (val == 0);
    }

    // To verify the credentials of the user
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

    // To add new user to the database
    public boolean addUserToUserDetails(String userName, String password, String eMail, String phoneNumber) {
        SQLiteDatabase userDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("password", password);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("eMail", eMail);
        contentValues.put("cart", " ");
        contentValues.put("orderDetails", " ");
        long insert = userDataBase.insert("userDetails", null, contentValues);
        // returns false if the data does not add on the sqlite DB.
        return insert != -1;
    }

    // to get the particular user data and store in the user Model class
    public userDetail getUserData(String userName) {
        SQLiteDatabase userDataBase = this.getWritableDatabase();
        Cursor matchUserName = userDataBase.rawQuery("SELECT * FROM userDetails WHERE userName = ?", new String[]{String.valueOf(userName)});
        userDetail value = null;
        matchUserName.moveToFirst();
        if (matchUserName.getCount() != 0) {
            String id = String.valueOf(matchUserName.getInt(0));
            String Name = matchUserName.getString(1);
            String password = matchUserName.getString(2);
            String eMail = matchUserName.getString(3);
            String phoneNumber = matchUserName.getString(4);
            String cart = matchUserName.getString(5);
            String orderDetails = matchUserName.getString(6);
            value = new userDetail(id, Name, password, eMail, phoneNumber, cart, orderDetails);
        }
        return value;
    }

    // To update the user data in the data base
    public void updateUserData(String userName, String cart, String orderDetails) {
        SQLiteDatabase userDataBase = this.getWritableDatabase();
        String[] tableNames = {"Id", "userName", "password", "eMail", "phoneNumber", "cart", "orderDetails"};
        ContentValues contentValues = new ContentValues();
        contentValues.put("cart", cart);
        contentValues.put("orderDetails", orderDetails);
        userDataBase.update("userDetails", contentValues, "userName=?", new String[]{userName});
    }

    public boolean addAddress(String userName, String name, String houseNo, String streetName, String state, String pinCode, String phoneNumber){
        SQLiteDatabase userDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("houseNo", houseNo);
        contentValues.put("streetName", streetName);
        contentValues.put("state", state);
        contentValues.put("pinCode", pinCode);
        contentValues.put("phoneNumber", phoneNumber);
        long insert = userDataBase.insert(userName + "_Address", null, contentValues);
        // returns false if the data does not add on the sqlite DB.
        return insert != -1;
    }

    public ArrayList<AddressDetails> getAddress(String userName){
        ArrayList <AddressDetails> addressList;
        addressList = new ArrayList<>();
        SQLiteDatabase userAddressDB = this.getReadableDatabase();
        Cursor address = userAddressDB.rawQuery("SELECT * FROM "+userName + "_Address",null);
        address.moveToFirst();
        do{
            float id = address.getFloat(0);
            String name = address.getString(1);
            String houseNo = address.getString(2);
            String streetName = address.getString(3);
            String state = address.getString(4);
            String pinCode = address.getString(5);
            String phoneNumber = address.getString(6);
            addressList.add(new AddressDetails(id, name,houseNo, streetName, state, pinCode, phoneNumber));
        }while(address.moveToNext());
        return addressList;
    }
}
