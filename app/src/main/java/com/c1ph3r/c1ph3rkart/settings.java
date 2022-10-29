package com.c1ph3r.c1ph3rkart;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.c1ph3r.c1ph3rkart.Model.userDetail;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class settings extends Fragment {
    View view;
    TextView  orderHistory, logout, userName, userEmail;
    ConstraintLayout userDetailsLayout;
    MaterialButton loginToContinue;

    public settings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        if (view != null) {
            orderHistory = view.findViewById(R.id.orderHistory);
            logout = view.findViewById(R.id.logout);
            userName = view.findViewById(R.id.userUserName);
            userEmail = view.findViewById(R.id.userUserEMail);
            userDetailsLayout = view.findViewById(R.id.layoutForUserDetails);
            loginToContinue = view.findViewById(R.id.loginToContinue);
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userId", MODE_PRIVATE);
            String user = sharedPreferences.getString("userName", "");
            UserDataBaseHelper userDB = new UserDataBaseHelper(requireActivity());
            userDetail userData = userDB.getUserData(user);

            if (user.equals("")) {
                logout.setVisibility(View.GONE);
                userDetailsLayout.setVisibility(View.GONE);
                loginToContinue.setVisibility(View.VISIBLE);
            }else{
                logout.setVisibility(View.VISIBLE);
                userDetailsLayout.setVisibility(View.VISIBLE);
                loginToContinue.setVisibility(View.GONE);
                userName.setText(userData.getUserName());
                userEmail.setText(userData.getEMail());
            }



            loginToContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(requireActivity(), LoginScreen.class);
                    startActivity(intent);
                }
            });



            logout.setOnClickListener(userLogoutView -> {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(requireActivity());
                alertDialogBuilder.setTitle("C1ph3R Kart!").setMessage("Do you Want to Logout?").setPositiveButton("No", (dialogInterface, i1) -> {
                }).setNegativeButton("yes", (dialogInterface, i1) -> {
                    SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences("userId", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("userName", "");
                    editor.apply();
                    Intent intent = new Intent(requireActivity(), Dashboard.class);
                    startActivity(intent);
                    requireActivity().finish();
                }).show();
            });
        }
        return view;
    }
}