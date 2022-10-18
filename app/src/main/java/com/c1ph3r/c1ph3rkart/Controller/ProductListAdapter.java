package com.c1ph3r.c1ph3rkart.Controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c1ph3r.c1ph3rkart.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    public ProductListAdapter(Context context,)

    @NonNull
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Insert the Recycle viewer layout to the assigned layout
        // Gives look to the Recycle viewer row.
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.MyViewHolder holder, int position) {
        // Decides the no of items to be displayed in the layout.
        // Binds the layout which are going to display. these values are taken from the garbage.
        // Store the values which are already displayed in some garbage.
        // It is basically a recycler.
    }

    @Override
    public int getItemCount() {
        // No of items to be displayed.
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // Like OnCreate method.
        // It takes the layout and assign it to the recycle viewer.
        TextView productName, ratings, price;
        ImageView thumbnailImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            ratings = itemView.findViewById(R.id.ratings);
            price = itemView.findViewById(R.id.price);
            thumbnailImage = itemView.findViewById(R.id.thumbnailImage);
        }
    }
}
