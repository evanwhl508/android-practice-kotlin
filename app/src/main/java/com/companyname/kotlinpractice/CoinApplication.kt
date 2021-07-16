package com.companyname.kotlinpractice

import android.app.Application
import com.companyname.kotlinpractice.realm.RealmManager
import com.companyname.kotlinpractice.room.RoomManager
import io.realm.Realm

class CoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        RealmManager.instance.initialize(this)
//        RoomManager.instance.init(this)
    }
}