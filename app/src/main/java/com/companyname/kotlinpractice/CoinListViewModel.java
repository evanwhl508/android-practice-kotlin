package com.companyname.kotlinpractice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Observable;

public class CoinListViewModel extends ViewModel {
    public MutableLiveData<String> searchStr = new MutableLiveData<>();
    public MutableLiveData<Integer> curSpinnerPosition = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Coin>> ldCoin = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Coin>> filteredCoin = new MutableLiveData<>();

    public LiveData<ArrayList<Coin>> getFilteredCoin() {
        return filteredCoin;
    }

    public CoinListViewModel() {
    }

    public Observable<ArrayList<Coin>> getCoins(String curr) {
        return CoinRepo.getCoins(curr)
//                .onErrorReturnItem()
                .doOnNext(coins -> {
                    ldCoin.postValue(coins);
                    filteredCoin.postValue(coins);
                });
    }

    public void filterCoins() {
        ArrayList<Coin> coins = ldCoin.getValue();
        List<Coin> filtered_list = coins;
        if (coins != null) {
            if (searchStr.getValue() != null) {
                filtered_list = coins.stream()
                        .filter(c ->
                                c.getSymbol().toLowerCase().contains(searchStr.getValue().toLowerCase()) ||
                                        c.getName().toLowerCase().contains(searchStr.getValue().toLowerCase()))
                        .collect(Collectors.toList());
                filteredCoin.postValue(new ArrayList<Coin>(filtered_list));
            }
        }
        Log.e("VM", "filterCoins: " + filtered_list.toString());
    }
}
