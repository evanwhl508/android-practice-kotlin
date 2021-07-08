package com.companyname.kotlinpractice;

import com.companyname.kotlinpractice.retrofit.CoinService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class CoinRepo {

    private CoinRepo() {}

    public static Observable<ArrayList<Coin>> getCoins(String curr) {
        return CoinService.instance.getCoins(curr);
    }

    public static Observable<CoinDetail> getCoinById(String id) {
        return CoinService.instance.getCoinById(id);
    }

    public static Observable<CoinPriceHistory> getCoinPriceHistory(String id) {
        return CoinService.instance.getCoinPriceHistory(id);
    }

    public static Observable<List<CoinMarketInfo>> getCoinMarket(String id) {
        return CoinService.instance.getCoinMarket(id);
    }
}
