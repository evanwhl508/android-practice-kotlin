package com.companyname.kotlinpractice;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.companyname.kotlinpractice.databinding.FragmentCoinPriceHistoryBinding;
import com.companyname.kotlinpractice.databinding.FragmentCoinPriceHistoryListBinding;
import com.companyname.kotlinpractice.placeholder.PlaceholderContent.PlaceholderItem;
//import com.companyname.kotlinpractice.databinding.FragmentCoinPriceHistoryBinding;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CoinPriceHistoryRecyclerViewAdapter extends RecyclerView.Adapter<CoinPriceHistoryRecyclerViewAdapter.ViewHolder> {

    private CoinPriceHistory mValues;
    private Context context;
    private FragmentCoinPriceHistoryListBinding binding;

    public CoinPriceHistoryRecyclerViewAdapter(Context context, CoinPriceHistory items) {
        this.mValues = items;
        this.context = context;
    }

    public void setmValues(CoinPriceHistory mValues) {
        this.mValues = mValues;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCoinPriceHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setup(mValues, position);
    }

    @Override
    public int getItemCount() {
        if (mValues.getRecord().isEmpty()){
            return 0;
        }
        return mValues.getRecord().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentCoinPriceHistoryBinding binding) {
            super(binding.getRoot());
            mIdView = binding.tvItemName;
            mContentView = binding.tvItemContent;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        public void setup(CoinPriceHistory mValues, Integer position){
            List<Double> record_list = mValues.getRecord().get(position);
            Double timestamp = record_list.get(0);
            long long_ts = Math.round(timestamp);
            Double price = record_list.get(1);
            DecimalFormat time_formatter = new DecimalFormat("#0");
            DecimalFormat price_formatter = new DecimalFormat("#");
            this.mIdView.setText(getDate(long_ts));
            this.mContentView.setText(String.format("%.8f", price));
        }

        private String getDate(long time) {
            TimeZone tz = TimeZone.getTimeZone("Asia/Hong_Kong");
            Calendar cal = Calendar.getInstance(Locale.CHINESE);
            cal.setTimeZone(tz);
            cal.setTimeInMillis(time * 1000);
            String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();
            return date;
        }
    }
}