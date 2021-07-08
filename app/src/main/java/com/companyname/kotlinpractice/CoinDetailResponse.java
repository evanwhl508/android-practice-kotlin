package com.companyname.kotlinpractice;

import com.google.gson.annotations.SerializedName;

public class CoinDetailResponse {
    @SerializedName("coin")
    public CoinDetail coin = new CoinDetail();
}
