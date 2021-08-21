package com.companyname.kotlinpractice.ui.main

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.companyname.kotlinpractice.PriceAlertRecyclerViewAdapter
import com.companyname.kotlinpractice.R
import com.companyname.kotlinpractice.entity.UserSpotBalance
import com.companyname.kotlinpractice.entity.UserTransaction
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.companyname.kotlinpractice.ui.main.placeholder.PlaceholderContent
import com.google.gson.Gson

/**
 * A fragment representing a list of Items.
 */
class TransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_list, container, false)
        val emptyList = arrayListOf<UserTransaction>()
        val rvAdapter = MyTransactionRecyclerViewAdapter()
        val rv: RecyclerView = view.findViewById(R.id.list)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(context)
//      Add separation line
        val dec = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        dec.setDrawable(ColorDrawable(ContextCompat.getColor(this.requireContext(),R.color.black)))
        rv.addItemDecoration(dec)

        val username = "test"
        FirestoreManager.instance.db
            .collection("users/$username/transaction")
            .addSnapshotListener { value, error ->
                error?.let {
                    Log.e("1111", it.toString())
                } ?: run {
                    val transactionList = arrayListOf<UserTransaction>()
                    value?.documents?.mapNotNull { it.data }?.forEach { data ->
                        Log.e("transaction data", "onCreateView: ${data["symbol"]} & ${data["amount"]}", )
                        var itemAmount = data["amount"]
                        var itemPrice = data["price"]
                        if (itemAmount is Long) {
                            itemAmount = itemAmount.toDouble()
                        }
                        if (itemPrice is Long) {
                            itemPrice = itemPrice.toDouble()
                        }
                        val b = Gson().fromJson(data.toString(), UserTransaction::class.java)
//                        val b = UserTransaction(
//                            symbol = data["symbol"] as String,
//                            amount = itemAmount as Double,
//                            direction = data["direction"] as String,
//                            price = itemPrice as Double,
//                            timestamp = data["timestamp"] as Long,
//                        )
                        transactionList.add(b)
                    }
                    Log.e("transaction data", "$transactionList", )
                    rvAdapter.setTransactions(transactionList)

                }
            }
        return view
    }
}