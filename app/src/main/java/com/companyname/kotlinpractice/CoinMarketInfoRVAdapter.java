package com.companyname.kotlinpractice;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.companyname.kotlinpractice.placeholder.PlaceholderContent.PlaceholderItem;
import com.companyname.kotlinpractice.databinding.FragmentCoinMarketInfoBinding;

import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CoinMarketInfoRVAdapter extends RecyclerView.Adapter<CoinMarketInfoRVAdapter.ViewHolder> {

    private List<CoinMarketInfo> market;

    public CoinMarketInfoRVAdapter(List<CoinMarketInfo> market) {
        this.market = market;
    }

    public void setMarket(List<CoinMarketInfo> market) {
        this.market = market;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCoinMarketInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setup(market.get(position));
    }

    @Override
    public int getItemCount() {
        return market.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CoinMarketInfo coinMarketInfo;
        FragmentCoinMarketInfoBinding binding;

        public ViewHolder(FragmentCoinMarketInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.executePendingBindings();
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "coinMarketInfo=" + coinMarketInfo +
                    '}';
        }

        void setup(CoinMarketInfo coinMarketInfo) {
            binding.setMarket(coinMarketInfo);
        }
    }
}