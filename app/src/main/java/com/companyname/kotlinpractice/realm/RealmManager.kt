package com.companyname.kotlinpractice.realm

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule

class RealmManager private constructor() {

//    lateinit var realmCoin: Realm
    lateinit var realmDeposit: Realm
    private set

    companion object {
        val instance = RealmManager()
    }

    init {

    }

    fun initialize(context: Context) {
        Realm.init(context)
//        realm = Realm.getDefaultInstance()

//        val coinConfig = RealmConfiguration
//                    .Builder()
//                    .modules(RealmCoinModule())
//                    .schemaVersion(1)
//                    .migration(Migration())
//                    .build()
        val depositConfig = RealmConfiguration
                    .Builder()
                    .modules(RealmDepositModule())
                    .schemaVersion(2)
                    .migration(DepositMigration())
                    .build()

//        realmCoin = Realm.getInstance(coinConfig)
        realmDeposit = Realm.getInstance(depositConfig)
    }
}

//@RealmModule(classes = [RealmCoin::class])
//open class RealmCoinModule

@RealmModule(classes = [RealmDeposit::class])
open class RealmDepositModule