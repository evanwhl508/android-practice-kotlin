package com.companyname.kotlinpractice.entity;

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class UserTransaction(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("direction") val direction: String,
    @SerializedName("timestamp") val timestamp: Long,
) {
    fun getFormattedAmount(): String {
        Log.e("entity", "getFormattedAmount: $amount")
        return amount.toString()
    }
    fun getFormattedTimestamp(): String {
        return timestamp.toString()
    }
    fun getFormattedPrice(): String {
        return price.toString()
    }

    fun getDateTime(): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
        val netDate = Date(timestamp)
        return sdf.format(netDate)
//        return Date(timestamp).toString()
    }
}
