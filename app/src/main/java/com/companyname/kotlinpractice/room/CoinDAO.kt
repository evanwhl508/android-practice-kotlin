package com.companyname.kotlinpractice.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface CoinDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: RoomCoin?): Maybe<Long>

    @Query("SELECT * FROM Coins")
    fun getAll(): Flowable<List<RoomCoin>>
}