package com.companyname.kotlinpractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import java.util.*
import kotlin.collections.ArrayList

class PersonalInfoViewModel : ViewModel() {
    var checkBox = MutableLiveData<Boolean>(false)
    var ldCoin = MutableLiveData<ArrayList<Coin>>()

    fun updateCoin(coins: ArrayList<Coin>) {
        ldCoin.postValue(coins)
    }

    companion object {

    }


    fun getCoins(curr: String?): Observable<ArrayList<Coin>> {
        return CoinRepo.getCoins(curr) //                .onErrorReturnItem()
            .doOnNext { coins: ArrayList<Coin>? ->
                coins?.let {
                    ldCoin.postValue(coins)
                }
            }
    }

}