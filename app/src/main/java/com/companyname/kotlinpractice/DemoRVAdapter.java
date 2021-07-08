package com.companyname.kotlinpractice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.companyname.kotlinpractice.R;
import com.companyname.kotlinpractice.databinding.LayoutListItemBinding;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class DemoRVAdapter extends RecyclerView.Adapter<DemoRVAdapter.ViewHolder> {
    private List<Coin> coins;

    DemoRVAdapter(Context context, List<Coin> coins){
        this.coins = coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DemoRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutListItemBinding binding = LayoutListItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoRVAdapter.ViewHolder holder, int position) {
        Log.e("adapter", "onBindViewHolder: " + position );
        Log.e("adapter", "onBindViewHolder: " + coins.get(position) );
        Coin coin = coins.get(position);
        holder.bind(coin);
    }

    @Override
    public int getItemCount() {
        if (coins == null) {
            return 0;
        }
        return coins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutListItemBinding binding;
        private Coin coin;

        void bind(Coin coin) {
            binding.setCoin(coin);
            this.coin = coin;
        }

        public ViewHolder(@NonNull LayoutListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.getRoot().setOnClickListener(v -> {
                if (coin != null) {
                    CoinDetailActivity.start(v, coin, binding.tvName, binding.ivIcon);
                }
            });
            binding.executePendingBindings();
        }
    }
}
