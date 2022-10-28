package com.c1ph3r.c1ph3rkart;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class settings extends Fragment {
    View view;
    TextView userDetails, orderHistory, logout;
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
        view =  inflater.inflate(R.layout.fragment_settings, container, false);
        if(view!=null){
            userDetails = view.findViewById(R.id.userDetails);
            orderHistory = view.findViewById(R.id.orderHistory);
            logout = view.findViewById(R.id.logout);
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userId", MODE_PRIVATE);
            String user = sharedPreferences.getString("userName","");

            if(user.equals(""))
                logout.setVisibility(View.GONE);

            userDetails.setOnClickListener(userStatusView -> {
                String userName = sharedPreferences.getString("userName","");
                if(userName.equals("")){
                    Toast.makeText(requireActivity(), "Login to Continue!" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(requireActivity(), LoginScreen.class);
                    startActivity(intent);
                }else
                    Toast.makeText(requireActivity(), "hello " + userName, Toast.LENGTH_SHORT).show();
            });

            logout.setOnClickListener(userLogoutView -> {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(requireActivity());
                alertDialogBuilder.setTitle("C1ph3R Kart!").setMessage("Do you Want to Logout?").setPositiveButton("No", (dialogInterface, i1) -> {}).setNegativeButton("yes", (dialogInterface, i1) -> {
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