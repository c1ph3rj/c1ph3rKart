package com.c1ph3r.c1ph3rkart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.c1ph3r.c1ph3rkart.Adapter.ProductListAdapter;
import com.c1ph3r.c1ph3rkart.Adapter.productOnClick;
import com.c1ph3r.c1ph3rkart.DBHealper.ListOfProductsHelper;
import com.c1ph3r.c1ph3rkart.DBHealper.UserDataBaseHelper;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.Model.userDetail;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.CheckedOutputStream;

public class checkoutCart extends Fragment implements productOnClick {
    View view;
    RecyclerView cartList;
    ShimmerFrameLayout loading;
    ArrayList<ProductList> cart;
    ListOfProductsHelper listOfProducts;
    String userName;
    JsonArray cartSP;
    userDetail user;
    MaterialButton checkout;
    UserDataBaseHelper userData;

    public checkoutCart() {
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
        view = inflater.inflate(R.layout.fragment_checkout_cart, container, false);

        if (view != null) {
            listOfProducts = new ListOfProductsHelper(requireActivity());
            cartList = view.findViewById(R.id.cartListViewerC);
            loading = view.findViewById(R.id.loadingScreenC);
            checkout = view.findViewById(R.id.proceedToBuy);

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userId", Context.MODE_PRIVATE);
            userName = sharedPreferences.getString("userName", "");
            if (userName.equals("")) {
                localCart();
            } else
                SQLCart(userName);

            checkout.setOnClickListener(view -> {
                Intent intent;
                if(!(cartSP.size()==0)){
                    if(userName.equals("")) {
                        Toast.makeText(requireActivity(), "Login To Continue", Toast.LENGTH_SHORT).show();
                        intent = new Intent(requireActivity(), LoginScreen.class);
                    }else{
                        intent = new Intent(requireActivity(), ConfirmToBuy.class);
                    }
                    startActivity(intent);
                }else
                    Toast.makeText(requireActivity(), "Your Cart is Empty!", Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }

    private void SQLCart(String userName) {
        try {
            userData = new UserDataBaseHelper(requireActivity());
            user = userData.getUserData(userName);
            cartSP = JsonParser.parseString(user.getCart()).getAsJsonArray();
            if(!cartSP.isJsonNull()){
                cart = listOfProducts.getProductByID(cartSP);
                setRecycleViewAdapter(cart);
            }
        }catch (Exception e) {
            loading.setVisibility(View.GONE);
            Toast.makeText(requireActivity(), "Cart is Empty.", Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }

    private void localCart() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("Cart", "none").equals("none")) {
            cartSP = JsonParser.parseString(sharedPreferences.getString("Cart", "")).getAsJsonArray();
            cart = listOfProducts.getProductByID(cartSP);
            setRecycleViewAdapter(cart);
        } else {
            loading.setVisibility(View.GONE);
            Toast.makeText(requireActivity(), "Cart is empty", Toast.LENGTH_SHORT).show();
        }


    }

    // Setting the recycle view adapter with the array list.
    private void setRecycleViewAdapter(ArrayList<ProductList> productLists) {
        try {
            loading.stopShimmer();
            loading.setVisibility(View.GONE);
            cartList.setVisibility(View.VISIBLE);
            ProductListAdapter adapter = new ProductListAdapter(requireActivity(), productLists, this);
            cartList.setAdapter(adapter);
            cartList.setLayoutManager(new LinearLayoutManager(getActivity()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // On click of the recycle view item: to display the particular product in a Activity.
    @Override
    public void onClickAnItem(int position) {
        cart.remove(position);
        cartSP.remove(position);
        if (userName.equals("")) {
            SharedPreferences Cart = requireActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE);
            SharedPreferences.Editor editCart = Cart.edit();
            editCart.putString("Cart", cartSP.toString());
            editCart.apply();
        }else{
            userData.updateUserData(userName, cartSP.toString(), " ");
        }
        setRecycleViewAdapter(cart);
    }
}