package com.c1ph3r.c1ph3rkart.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.c1ph3r.c1ph3rkart.R;

import java.util.ArrayList;

public class ProductImagesAdapter extends PagerAdapter {
    Context context;
    ArrayList images;

    public ProductImagesAdapter(Context context, ArrayList images){
        this.context = context;
        this.images = images;

    }

    // No of items to be displayed
    @Override
    public int getCount() {
        return images.size();
    }

    // check the view of the object.
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    // Like On Create method.
    // Used to instantiate items while created.
    // And returns the image view to the view.
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView image = new ImageView(context);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(String.valueOf(images.get(position))).into(image);
        container.addView(image);
        return image;
    }

    // To remove the used items.
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView) object);
    }
}
