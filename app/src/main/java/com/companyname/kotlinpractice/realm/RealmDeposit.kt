package com.companyname.kotlinpractice.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RealmDeposit: RealmObject() {
    @PrimaryKey
    var id: String = ""

    @Required
    var amount: String = "0"
}