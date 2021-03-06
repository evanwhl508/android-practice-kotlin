package com.companyname.kotlinpractice.room

import android.content.Context
import androidx.room.Room

class RoomManager private constructor() {
    lateinit var db: CoinDatabase
    private set

    companion object {
        @JvmStatic
        val instance = RoomManager()
    }

    fun init(context: Context) {
        db = Room.databaseBuilder<CoinDatabase>(context.applicationContext, CoinDatabase::class.java, "test-db")
            .allowMainThreadQueries()
//            .addMigrations(CoinDatabase.MIGRATION_1_2)
            .build()
    }
}