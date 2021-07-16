package com.companyname.kotlinpractice.realm

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RealmCoin(): RealmObject() {
    @PrimaryKey
    var coinId: String = ""

    @Required
    var coinName: String = ""

    @Required
    var test:String? = ""
}