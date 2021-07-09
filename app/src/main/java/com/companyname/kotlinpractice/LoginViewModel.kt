package com.companyname.kotlinpractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import java.util.ArrayList

import io.reactivex.rxjava3.core.Observable

class LoginViewModel() : ViewModel() {

    var ldCoin = MutableLiveData<ArrayList<Coin>>()

    var name = MutableLiveData<String>("test")
    var password = MutableLiveData<String>("123456")

//    MediatorLiveData ===== CombineLatest
    var btnEnable = MediatorLiveData<Boolean>()

    init {
        btnEnable.addSource(name) { checkBtnEnable() }
        btnEnable.addSource(password) { checkBtnEnable() }
    }

    private fun checkBtnEnable() {
        if (name.value == null || password.value == null) {
            btnEnable.setValue(false)
            return
        } else if (name.value.isNullOrBlank() || password.value.isNullOrBlank()) {
            btnEnable.setValue(false)
            return
        }
        else {
            btnEnable.setValue(true)
            return
        }

    }

    fun getCoins() : Observable<ArrayList<Coin>> {
        return CoinRepo.getCoins("USD")
//                .onErrorReturnItem()
                .doOnNext { ldCoin.postValue(it) }
    }
}
