package com.companyname.kotlinpractice;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ViewDataBindingAdapter {

    @BindingAdapter("app:visibleOrGone")
    static public void setVisibleOrGone(View v, boolean visible) {
        v.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("app:imageUrl")
    static public void setImageUrl(ImageView v, String url) {
        Glide.with(v.getContext()).load(url).into(v);
    }

    @BindingAdapter("app:signedDoublerColor")
    static public void setImageUrl(TextView v, Double d ) {
        if (d < 0) {
            v.setTextColor(v.getContext().getColor(R.color.alert_red));
        }
        else{
            v.setTextColor(Color.parseColor("#228B22"));
        }
    }
}
