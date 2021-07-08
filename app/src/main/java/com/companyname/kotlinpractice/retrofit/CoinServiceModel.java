package com.companyname.kotlinpractice.retrofit;

import com.companyname.kotlinpractice.CoinDetail;
import com.companyname.kotlinpractice.CoinDetailResponse;
import com.companyname.kotlinpractice.CoinMarketInfo;
import com.companyname.kotlinpractice.CoinPriceHistory;
import com.companyname.kotlinpractice.CoinResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoinServiceModel {

    @GET("public/v1/coins?skip=0&limit=10")
    Observable<CoinResponse> getCoins(@Query("currency") String currency);

    @GET("public/v1/coins/{coinId}?currency=USD")
    Observable<CoinDetailResponse> getCoinById(@Path("coinId") String coinId);

    @GET("public/v1/charts?period=24h")
    Observable<CoinPriceHistory> getCoinPriceHistory(@Query("coinId") String coinId);

    @GET("public/v1/markets")
    Observable<List<CoinMarketInfo>> getCoinMarket(@Query("coinId") String coinId);

}
