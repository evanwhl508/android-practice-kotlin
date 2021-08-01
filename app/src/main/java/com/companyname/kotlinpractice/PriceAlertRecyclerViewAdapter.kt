package com.companyname.kotlinpractice

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

import com.companyname.kotlinpractice.databinding.FragmentPriceAlertBinding
import com.companyname.kotlinpractice.firestore.FirestoreManager
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PriceAlertRecyclerViewAdapter(var context: Context?) : RecyclerView.Adapter<PriceAlertRecyclerViewAdapter.ViewHolder>() {
    private var values: ArrayList<PriceAlert> = arrayListOf()

    fun setItems(v: ArrayList<PriceAlert>) {
        Log.e("set Item", "setItems: $v")
        values = v
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentPriceAlertBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Log.e("item.coinId", "onBindViewHolder: $item.coinId", )
        Log.e("item.price", "onBindViewHolder: $item.price", )
        holder.idView.text = item.coinId
        holder.contentView.text = item.price
        holder.btnView.setOnClickListener{
            context?.let {
                val pref = context!!.getSharedPreferences("firebase", Context.MODE_PRIVATE)
                val uid = pref?.getString("user", null)
                uid?.let {
                    FirestoreManager.instance.db
                        .collection("price_alert")
                        .document("$uid")
                        .collection("coins")
                        .document(item.coinId)
                        .delete()
                        .addOnSuccessListener {
                            Log.e(
                                "clear button",
                                "DocumentSnapshot successfully deleted ${item.coinId}!"
                            )
                            this.values.removeAt(position)
                            this.notifyItemRemoved(position)
                        }
                        .addOnFailureListener { e ->
                            Log.e(
                                "clear button",
                                "Error deleting document"
                            )
                        }
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPriceAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val btnView: ImageButton = binding.ibRemove

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}