package com.companyname.kotlinpractice.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RoomCoin::class], version = 1)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun coinDao(): CoinDAO?

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Coins"
                        + "ADD COLUMN useful INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}