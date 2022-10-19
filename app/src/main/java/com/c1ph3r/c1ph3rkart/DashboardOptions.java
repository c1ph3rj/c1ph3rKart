package com.c1ph3r.c1ph3rkart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class DashboardOptions extends Fragment {

    public DashboardOptions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_options, container, false);
    }

    public void onStart() {
        super.onStart();
        View view = getView();
        assert view != null;
        MaterialButton  laptops, fragrances, skinCare, groceries, homeDecorations;
        Button smartPhone;
        smartPhone = view.findViewById(R.id.smartPhoneBtn);
        laptops = view.findViewById(R.id.laptopBtn);
        fragrances = view.findViewById(R.id.fragrancesBtn);
        skinCare = view.findViewById(R.id.skinCareBtn);
        groceries = view.findViewById(R.id.groceriesBtn);
        homeDecorations = view.findViewById(R.id.homeDecorationsBtn);

        smartPhone.setOnClickListener(view12 -> filterData("smartphones"));

        laptops.setOnClickListener(view1 -> filterData("laptops"));

        fragrances.setOnClickListener(view13 -> filterData("fragrances"));

        groceries.setOnClickListener(view14 -> filterData("groceries"));

        homeDecorations.setOnClickListener(view15 -> filterData("home-decoration"));

        skinCare.setOnClickListener(view16 -> filterData("skincare"));


    }

    void filterData(String value){
        Intent intent = new Intent(getActivity(), ProductsInTheData.class);
        intent.putExtra("value", value);
        startActivity(intent);
        getActivity().finish();
    }
}