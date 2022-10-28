package com.c1ph3r.c1ph3rkart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.c1ph3r.c1ph3rkart.AddAddress;
import com.c1ph3r.c1ph3rkart.Model.AddressDetails;
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.R;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {
    Context context;
    ArrayList<AddressDetails> addressList;
    productOnClick itemOnClick;

    public AddressListAdapter(Context context, ArrayList<AddressDetails> addressList, productOnClick itemOnClick) {
        this.context = context;
        this.addressList = addressList;
        this.itemOnClick = itemOnClick;
    }

    @NonNull
    @Override
    public AddressListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Insert the Recycle viewer layout to the assigned layout
        // Gives look to the Recycle viewer row.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.address_list_item_layout, parent, false);
        return new AddressListAdapter.MyViewHolder(view, itemOnClick);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.MyViewHolder holder, int position) {
        // Decides the no of items to be displayed in the layout.
        // Binds the layout which are going to display. these values are taken from the garbage.
        // Store the values which are already displayed in some garbage.
        // It is basically a recycler.

        holder.Name.setText(addressList.get(position).getName());
        holder.Address.setText(addressList.get(position).getHouseNo() + ",\n" + addressList.get(position).getStreetName() + ",\n"+ addressList.get(position).getState() + ",\n" + addressList.get(position).getPinCode() + ".");
        holder.PhoneNumber.setText(addressList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        // No of items to be displayed.
        return addressList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Like OnCreate method.
        // It takes the layout and assign it to the recycle viewer.
        TextView Name, PhoneNumber, Address;

        public MyViewHolder(@NonNull View itemView, productOnClick itemOnClick) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            PhoneNumber = itemView.findViewById(R.id.phoneNumber);
            Address = itemView.findViewById(R.id.Address);

            itemView.setOnClickListener(view -> {
                if (itemOnClick != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemOnClick.onClickAnItem(position);
                    }
                }
            });
        }
    }
}
