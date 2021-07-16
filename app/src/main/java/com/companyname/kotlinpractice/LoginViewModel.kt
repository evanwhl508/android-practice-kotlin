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
        btnEnable.value = !(name.value.isNullOrBlank() || password.value.isNullOrBlank())
    }

    fun getCoins() : Observable<ArrayList<Coin>> {
        return CoinRepo.getCoins("USD")
//                .onErrorReturnItem()
                .doOnNext { ldCoin.postValue(it) }
    }
}
