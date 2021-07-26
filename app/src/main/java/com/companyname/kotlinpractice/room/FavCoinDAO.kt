package com.companyname.kotlinpractice.room

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface FavCoinDAO {
    @Query("SELECT * FROM FavCoins")
    fun getAllFavCoin(): Flowable<List<RoomFavCoin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavCoin(coin: RoomFavCoin?): Maybe<Long>

    @Delete
    fun delete(coin: RoomFavCoin?): Maybe<Int>
}