package com.companyname.kotlinpractice

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.google.firebase.firestore.ktx.toObjects
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class PriceAlertFragment : Fragment() {

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_price_alert_list, container, false)
        val rvAdapter = PriceAlertRecyclerViewAdapter(context)

        val rv: RecyclerView = view.findViewById(R.id.list)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(context)

//        rvAdapter.setItems(priceAlertList)

        val pref = activity?.getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val uid = pref?.getString("user", null)
        uid?.let {
            FirestoreManager.instance.db
                .collection("price_alert")
                .document("$uid")
                .collection("coins")
                .addSnapshotListener { value, error ->
                    error?.let {
                        Log.e("1111", it.toString())
                    } ?: run {
                        val priceAlertList = arrayListOf<PriceAlert>()
                        value?.let {
                            it.documents.forEach { doc ->
                                Log.e("price alert", "" + doc.data?.get("higher"))
                                Log.e("price alert", "" + doc.data?.get("name"))
                                if (doc.data?.get("id") != null && doc.data?.get("higher") != null) {
                                    val pa = PriceAlert(
                                        coinId = doc.data?.get("id") as String,
                                        price = doc.data?.get("higher") as String
                                    )
                                    priceAlertList.add(pa)
                                    rvAdapter.setItems(priceAlertList)
//                                rvAdapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
        }
//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//
//            }
//        }
        return view
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
            PriceAlertFragment()
    }
}