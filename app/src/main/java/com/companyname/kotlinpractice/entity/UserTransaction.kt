package com.companyname.kotlinpractice.entity;

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
}
