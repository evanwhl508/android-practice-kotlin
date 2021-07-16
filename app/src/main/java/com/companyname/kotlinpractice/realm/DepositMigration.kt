package com.companyname.kotlinpractice.realm

import android.util.Log
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

open class DepositMigration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var version = oldVersion
        val schema = realm.schema

//        version = 4L
        // Version 1 -> Version 2
    }

}