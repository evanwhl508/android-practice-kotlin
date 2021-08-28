package com.companyname.kotlinpractice

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.google.gson.Gson

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
                .collection("users/$uid/price_alert")
                .addSnapshotListener { value, error ->
                    error?.let {
//                        Log.e("1111", it.toString())
                    } ?: run {
                        val priceAlertList = arrayListOf<PriceAlert>()
                        value?.documents?.mapNotNull { it.data }?.forEach { data ->
//                            Log.e("doc data", "onCreateView: $data", )
                            val pa = Gson().fromJson(data.toString(),PriceAlert::class.java)
//                            Log.e("GSON", "$pa", )
                            priceAlertList.add(pa)
                            rvAdapter.setItems(priceAlertList)
                            }
                        }
                    }
        }
        return view
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
            PriceAlertFragment()
    }
}