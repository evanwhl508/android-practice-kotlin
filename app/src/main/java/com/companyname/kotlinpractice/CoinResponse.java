package com.companyname.kotlinpractice;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class CoinResponse {
    @SerializedName("coins")
    public ArrayList<Coin> coins = new ArrayList<>();
}
