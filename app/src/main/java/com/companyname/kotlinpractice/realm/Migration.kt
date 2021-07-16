package com.companyname.kotlinpractice.realm

import android.util.Log
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

open class Migration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var version = oldVersion
        val schema = realm.schema

        Log.e("2222", "$version")
//        version = 4L
        // Version 1 -> Version 2
        if (version == 1L) {
            schema["RealmCoin"]
                ?.addField("test", String::class.java, FieldAttribute.REQUIRED)
                ?.transform {obj -> obj["test"] = "testing"}
            version += 1
        }
        if (version == 2L) {
            schema["RealmCoin"]
                ?.transform {obj -> obj["test"] = "testing 2"}
            version += 1
        }
        if (version == 3L) {
            schema["RealmCoin"]
                ?.transform {obj -> obj["test"] = "testing 3"}
            version += 1
        }
        if (version == 4L) {
            schema["RealmCoin"]
                ?.transform {obj -> obj["test"] = "testing 4"}
            version += 1
        }
        if (version == 7L) {
            schema["RealmCoin"]
                ?.transform {obj -> obj["test"] = "hjkhkjhk"}
            version += 1
        }
    }

}