package com.companyname.kotlinpractice.retrofit;

import com.companyname.kotlinpractice.Coin;
import com.companyname.kotlinpractice.CoinDetail;
import com.companyname.kotlinpractice.CoinMarketInfo;
import com.companyname.kotlinpractice.CoinPriceHistory;
import com.companyname.kotlinpractice.CoinResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinService {
    public final static CoinService instance = new CoinService();
    private OkHttpClient client;
    private CoinServiceModel model;

    private CoinService() {
//        Set timeout
        client = new OkHttpClient.Builder().build();

        model = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.coinstats.app")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(CoinServiceModel.class);
    }

    public Observable<ArrayList<Coin>> getCoins(String currency) {
        return model.getCoins(currency).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).onTerminateDetach()
                .map(res -> res.coins);
    }

    public Observable<CoinDetail> getCoinById(String coinId) {
        return model.getCoinById(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).onTerminateDetach()
                .map(res -> res.coin);
    }

    public Observable<CoinPriceHistory> getCoinPriceHistory(String coinId) {
        return model.getCoinPriceHistory(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).onTerminateDetach();
    }

    public Observable<List<CoinMarketInfo>> getCoinMarket(String coinId) {
        return model.getCoinMarket(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).onTerminateDetach();
    }
}
