package com.companyname.kotlinpractice

import com.google.gson.annotations.SerializedName

class PriceAlert(
    @SerializedName("id") var coinId: String,
    @SerializedName("higher") var price: String) {
}