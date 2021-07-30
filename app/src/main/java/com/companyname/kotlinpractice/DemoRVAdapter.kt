package com.companyname.kotlinpractice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.companyname.kotlinpractice.databinding.LayoutListItemBinding;
import com.companyname.kotlinpractice.room.CoinDatabase;
import com.companyname.kotlinpractice.room.RoomCoin;
import com.companyname.kotlinpractice.room.RoomFavCoin;
import com.companyname.kotlinpractice.room.RoomManager;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import java.util.Collections;

class DemoRVAdapter : RecyclerView.Adapter<DemoRVAdapter.ViewHolder>() {
    private var coins: List<Coin> = Collections.emptyList()

    fun setCoins(coins: List<Coin>) {
        this.coins = coins
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.getContext());
        val binding: LayoutListItemBinding = LayoutListItemBinding.inflate(layoutInflater, parent, false);
        return ViewHolder(binding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin: Coin = coins.get(position);
        holder.bind(coin);
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    class ViewHolder(var binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var coin:Coin;

        fun bind(c: Coin) {
            binding.coinEntity = c;
            coin = c;
        }

        init {
            this.binding.root.setOnClickListener{
                CoinDetailActivity.start(it, coin, binding.tvName, binding.ivIcon)
            }

            this.binding.checkBoxFav.setOnClickListener{
                val c = RoomFavCoin().apply {
                    coinId = coin.id
                }
//                c.coinId=this.coin.getId();
                RoomManager.instance.db.favCoinDao()?.let{
                    if (this.binding.checkBoxFav.isChecked) {
                        it.insertFavCoin(c).subscribeOn(AndroidSchedulers.mainThread()).subscribe{
                            this.binding.checkBoxFav.setButtonDrawable(R.drawable.ic_baseline_star_24)
                        }
                    } else {
                        it.delete(c).subscribeOn(AndroidSchedulers.mainThread()).subscribe{
                            this.binding.checkBoxFav.setButtonDrawable(R.drawable.ic_baseline_star_border_24)
                        }
                    }
                    it.getAllFavCoin().subscribe{ res -> Log.e("Room", ": $res")}

                    }
//                if (this.binding.checkBoxFav.isChecked()) {
//                    RoomManager.instance.db.favCoinDao()?.insertFavCoin(c)
//                } else {
//                    RoomManager.instance.db.favCoinDao()?.delete(c)
//                }

            }
        }

//        public ViewHolder(@NonNull LayoutListItemBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//
//            this.binding.getRoot().setOnClickListener(v -> {
//                if (coin != null) {
//                    CoinDetailActivity.start(v, coin, binding.tvName, binding.ivIcon);
//                }
//            });
//
//            this.binding.checkBoxFav.setOnClickListener(v -> {
//                RoomFavCoin c = new RoomFavCoin();
//                c.coinId=this.coin.getId();
//                if (this.binding.checkBoxFav.isChecked()) {
//                    if (RoomManager.instance.db != null) {
//                        if (RoomManager.instance.db.favCoinDao() != null) {
//                            RoomManager.instance.db.favCoinDao().insertFavCoin(c);
//                        }
//                    }
//                }
//            });
////            binding.executePendingBindings();
//        }
    }
}
