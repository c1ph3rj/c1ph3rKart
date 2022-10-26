package com.c1ph3r.c1ph3rkart.Controller;

import android.database.sqlite.SQLiteDatabase;

import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;

public class AuthController {
    // Used to verify the user Login credentials.
    public int userLoginVerification(String userName, String password, UserDataBaseHelper userDB){
        return userDB.verifyTheUser(userName,password);
    }

    // Used to verify the new register Details.
    public int userRegistration(String userName, String password, String confirmPassword, String eMail, String phoneNumber, UserDataBaseHelper userDB){
        try {
            // User Name Verification.
            if(!userName.matches("^[a-zA-Z][0-9a-zA-Z_]{4,26}$")){
                return 1;
            }else if(!userDB.isUserNameInTheTable(userName))
                return 7;


            // E-Mail Verification.
            if (!eMail.matches("^[a-zA-Z]+[0-9a-zA-z]+[@][a-zA-Z0-9]+\\.[a-z]{2,}$"))
                return 5;
            else if(!userDB.isEMailInTheTable(eMail))
                return 8;

            // Password verification.
            if(password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\w\\W]{8,}$")){
                if(!password.equals(confirmPassword)){
                    return 3;
                }
            }else
                return 2;



            // Phone No verification.
            if (!phoneNumber.matches("^[6-9][0-9]{9}$"))
                return 4;
            else if(!userDB.isPhoneNumberInTheTable(phoneNumber))
                return 9;
            else{
                boolean val = userDB.addUserToUserDetails(userName, password, eMail, phoneNumber);
                if (val)
                    return 6;
                else
                    return 10;
            }
        }
        catch(Exception e){
            System.out.println(e + " | UserVerification");
            return 0;
        }
    }

}
