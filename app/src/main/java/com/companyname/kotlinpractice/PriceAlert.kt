package com.companyname.kotlinpractice

import com.google.gson.annotations.SerializedName

class PriceAlert(
    @SerializedName("id") var coinId: String,
    @SerializedName("price") var price: String,
    @SerializedName("timestamp") var timestamp: Long,
    @SerializedName("direction") var direction: String,
    ) {
}