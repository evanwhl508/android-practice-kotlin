package com.companyname.kotlinpractice

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide

class ViewDataBindingAdapter {

    companion object {

        @BindingAdapter("app:visibleOrGone")
        @JvmStatic
        fun setVisibleOrGone(v: View, visible: Boolean) {
            v.visibility = if (visible) {View.VISIBLE} else {View.GONE}
        }

        @BindingAdapter("app:updateFav")
        @JvmStatic
        fun setUpdateFav(v: CheckBox, isFav: Boolean) {
            if (isFav) {
                v.setButtonDrawable(R.drawable.ic_baseline_star_24)
            } else {
                v.setButtonDrawable(R.drawable.ic_baseline_star_border_24)
            }
        }

        @BindingAdapter("app:imageUrl")
        @JvmStatic
        fun setImageUrl(v: ImageView, url: String?)
        {
            Glide.with(v.context).load(url).into(v)
        }

        @BindingAdapter("app:signedDoublerColor")
        @JvmStatic
        fun setSignedDoublerColor(v: TextView, d: Double) = if (d < 0) {
            v.setTextColor(v.context.getColor(R.color.alert_red))
        } else {
            v.setTextColor(Color.parseColor("#228B22"))
        }
    }
}
