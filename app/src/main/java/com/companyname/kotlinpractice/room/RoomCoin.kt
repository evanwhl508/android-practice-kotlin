package com.companyname.kotlinpractice.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Coins")
class RoomCoin {

    @PrimaryKey
    var coinId: String = ""

    var coinName: String? = null

    @ColumnInfo(name = "created_time", typeAffinity = ColumnInfo.INTEGER)
    var time: Long = Calendar.getInstance().timeInMillis

//    var useful: Int = 0

}