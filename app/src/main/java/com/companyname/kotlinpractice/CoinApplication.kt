package com.companyname.kotlinpractice

import android.app.Application
import com.companyname.kotlinpractice.realm.RealmManager
import com.companyname.kotlinpractice.room.RoomManager
import io.realm.Realm
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "8ab08e18-1478-4cbc-8adb-800ea92b8519"

class CoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        RealmManager.instance.initialize(this)
        RoomManager.instance.init(this)

        initOneSignal()
    }

    private fun initOneSignal(){
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}