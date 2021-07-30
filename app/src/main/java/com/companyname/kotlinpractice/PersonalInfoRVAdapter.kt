package com.companyname.kotlinpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.companyname.kotlinpractice.databinding.LayoutListItemBinding

class PersonalInfoRVAdapter(coinList: List<Coin>):
    RecyclerView.Adapter<PersonalInfoRVAdapter.ViewHolder>() {

    fun setCoins(coinList: List<Coin>) {
        coins = coinList
        notifyDataSetChanged()
    }

    companion object {
        var coins: List<Coin> = emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutListItemBinding =
            LayoutListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coins[position])
    }

    override fun getItemCount(): Int {
        coins.let { return coins.size }
    }

    class ViewHolder(binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: LayoutListItemBinding
        private var coin: Coin? = null
        fun bind(c: Coin?) {
            binding.coinEntity = c
            this.coin = c
        }

        init {
            this.binding = binding
        }
    }
}