package com.companyname.kotlinpractice.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "FavCoins")
class RoomFavCoin {
    @PrimaryKey
    var coinId: String = ""
}