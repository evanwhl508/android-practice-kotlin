package com.companyname.kotlinpractice.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.companyname.kotlinpractice.databinding.FragmentTransactionBinding
import com.companyname.kotlinpractice.entity.UserTransaction
import com.companyname.kotlinpractice.ui.main.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyTransactionRecyclerViewAdapter(
) : RecyclerView.Adapter<MyTransactionRecyclerViewAdapter.ViewHolder>() {

    fun setTransactions(transactionList: List<UserTransaction>) {
        transactioins = transactionList
        notifyDataSetChanged()
    }

    companion object {
        var transactioins: List<UserTransaction> = emptyList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = transactioins[position]
//        holder.name.text = item.symbol
//        holder.price.text = item.amount.toString()
        holder.bind(item)

    }

    override fun getItemCount(): Int = transactioins.size

    inner class ViewHolder(var binding: FragmentTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var transaction: UserTransaction? = null
//        val name: TextView = binding.tvItemName
//        val price: TextView = binding.tvItemPrice

        fun bind(t: UserTransaction?) {
            binding.item = t
            this.transaction = t
        }
    }

}