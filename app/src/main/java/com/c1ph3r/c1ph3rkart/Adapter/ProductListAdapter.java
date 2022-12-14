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
import com.c1ph3r.c1ph3rkart.Model.ProductList;
import com.c1ph3r.c1ph3rkart.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductList> productLists;
    productOnClick itemOnClick;

    public ProductListAdapter(Context context, ArrayList<ProductList> productLists, productOnClick itemOnClick) {
        this.context = context;
        this.productLists = productLists;
        this.itemOnClick = itemOnClick;
    }

    @NonNull
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Insert the Recycle viewer layout to the assigned layout
        // Gives look to the Recycle viewer row.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_item_layout, parent, false);
        return new MyViewHolder(view, itemOnClick);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.MyViewHolder holder, int position) {
        // Decides the no of items to be displayed in the layout.
        // Binds the layout which are going to display. these values are taken from the garbage.
        // Store the values which are already displayed in some garbage.
        // It is basically a recycler.

        double discountPrice = (productLists.get(position).getDiscountPercentage() / 100) * productLists.get(position).getPrice();
        holder.productName.setText(productLists.get(position).getTitle());
        holder.price.setText("$ " + Math.ceil(productLists.get(position).getPrice() - discountPrice));
        holder.ratings.setText("Ratings: " + productLists.get(position).getRating());
        Glide.with(context).load(String.valueOf(productLists.get(position).getThumbnail())).into(holder.thumbnailImage);


    }

    @Override
    public int getItemCount() {
        // No of items to be displayed.
        return productLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Like OnCreate method.
        // It takes the layout and assign it to the recycle viewer.
        TextView productName, ratings, price;
        ImageView thumbnailImage;

        public MyViewHolder(@NonNull View itemView, productOnClick itemOnClick) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            ratings = itemView.findViewById(R.id.ratings);
            price = itemView.findViewById(R.id.price);
            thumbnailImage = itemView.findViewById(R.id.thumbnailImage);

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
