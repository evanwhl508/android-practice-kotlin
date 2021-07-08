package com.companyname.kotlinpractice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Coin>> ldCoin = new MutableLiveData<>();

    public MutableLiveData<String> name = new MutableLiveData<>("test");
    public MutableLiveData<String> password = new MutableLiveData<>("123456");

//    MediatorLiveData ===== CombineLatest
    public MediatorLiveData<Boolean> btnEnable = new MediatorLiveData<>();

    LoginViewModel() {
        btnEnable.addSource(name, s -> {
            checkBtnEnable();
        });
        btnEnable.addSource(password, s -> {
            checkBtnEnable();
        });
    }

    private void checkBtnEnable() {
        if (name.getValue() == null || password.getValue() == null) {
            btnEnable.setValue(false);
            return;
        } else if (name.getValue().length() == 0 || password.getValue().length() == 0) {
            btnEnable.setValue(false);
            return;
        }
        else {
            btnEnable.setValue(true);
            return;
        }

    }

    public Observable<ArrayList<Coin>> getCoins() {
        return CoinRepo.getCoins("USD")
//                .onErrorReturnItem()
                .doOnNext(coins -> ldCoin.postValue(coins));
    }
}
