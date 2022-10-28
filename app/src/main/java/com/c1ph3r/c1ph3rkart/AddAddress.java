package com.c1ph3r.c1ph3rkart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddAddress extends AppCompatActivity {
    TextInputEditText name, houseNo, streetView, pinCode, state, phoneNumber;
    MaterialButton submit, cancel;
    String userName;
    UserDataBaseHelper userDB = new UserDataBaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        name = findViewById(R.id.nameNewAdd);
        houseNo = findViewById(R.id.houseNoNewAdd);
        streetView = findViewById(R.id.streetNewAdd);
        pinCode = findViewById(R.id.pinCodeNewAdd);
        state = findViewById(R.id.StateFieldNewAdd);
        phoneNumber = findViewById(R.id.phoneNumberNewAdd);
        submit = findViewById(R.id.SubmitBtnNewAdd);
        cancel = findViewById(R.id.cancelNewAdd);

        SharedPreferences sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = userDB.addAddress(userName,name.getText().toString(),houseNo.getText().toString(), streetView.getText().toString(), state.getText().toString(),pinCode.getText().toString(), phoneNumber.getText().toString());
                if(result){
                    Toast.makeText(AddAddress.this, "successfully added.", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(AddAddress.this, "Failed to add.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddAddress.this, ConfirmToBuy.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAddress.this, ConfirmToBuy.class);
                startActivity(intent);
            }
        });
    }
}