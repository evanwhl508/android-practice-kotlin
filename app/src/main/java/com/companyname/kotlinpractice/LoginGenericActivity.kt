package com.companyname.kotlinpractice

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.companyname.kotlinpractice.databinding.ActivityLoginBinding
import com.companyname.kotlinpractice.realm.RealmCoin
import com.companyname.kotlinpractice.realm.RealmManager
import com.companyname.kotlinpractice.room.RoomCoin
import com.companyname.kotlinpractice.room.RoomManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.kotlin.where
import java.net.ContentHandler
import java.util.concurrent.TimeUnit
import kotlin.math.log

class LoginGenericActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    lateinit var res: RealmResults<RealmCoin>

    override val layoutId: Int = R.layout.activity_login

    override fun createViewModel() = LoginViewModel()

    override fun bindViewModel() {
        binding.model = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        ***SharedPreferences***
//        val pref = getSharedPreferences("test", Context.MODE_PRIVATE)
//        pref.edit().putInt("key1", 1).apply()
//
//        val t = pref.getInt("key1", 0)
//        Log.e("11111", t.toString() )

//        ***Realm***
//        res = RealmManager.instance.realm.where<RealmCoin>().findAllAsync()
//        res.addChangeListener { change ->
//            change.forEach {
//                Log.e("2222", "" + it)
//            }
//        }
//        RealmManager.instance.realm.executeTransactionAsync {
//            val coin = RealmCoin()
//            coin.coinId = "btc"
//            coin.coinName = "Bitcoin"
//            coin.test = "rewrw"
//            it.insertOrUpdate(coin)
//        }

//        ***Room***
        val coin = RoomCoin().apply {
                        coinId = "btc"
                        coinName = "Bitcoin"
                    }
//        RoomManager.instance.db.coinDao()?.let {
//            it.insert(coin).subscribe()
//            it.getAll().subscribe { Log.e("333", "" + it) }
//        }

        Observable.just(RoomManager.instance.db.coinDao())
            .subscribeOn(Schedulers.io())
            .subscribe { dao ->
                dao?.let {
                    it.insert(coin).subscribe()
                    it.getAll().subscribe { Log.e("333", "" + it) }
                }
            }
    }

}