package com.companyname.kotlinpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.companyname.kotlinpractice.databinding.LayoutBalanceListBinding
import com.companyname.kotlinpractice.databinding.LayoutListItemBinding
import com.companyname.kotlinpractice.entity.UserSpotBalance

class PersonalInfoRVAdapter(coinList: List<UserSpotBalance>):
    RecyclerView.Adapter<PersonalInfoRVAdapter.ViewHolder>() {

    fun setCoins(coinList: List<UserSpotBalance>) {
        coins = coinList
        notifyDataSetChanged()
    }

    companion object {
        var coins: List<UserSpotBalance> = emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutBalanceListBinding =
            LayoutBalanceListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coins[position])
    }

    override fun getItemCount(): Int {
        coins.let { return coins.size }
    }

    class ViewHolder(var binding: LayoutBalanceListBinding) : RecyclerView.ViewHolder(binding.root) {
        private var coin: UserSpotBalance? = null
        fun bind(c: UserSpotBalance?) {
            binding.item = c
            this.coin = c
        }

    }
}