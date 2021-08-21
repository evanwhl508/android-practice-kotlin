package com.companyname.kotlinpractice.entity

import androidx.databinding.ObservableField
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.text.DecimalFormat
import java.util.*

@IgnoreExtraProperties
class UserSpotBalance(
    val symbol: String,
    val amount: Double
) {

    @Exclude
    fun getFormattedAmount(): String {
        return amount.toString()
    }

    @Exclude
    fun getDiaplsyedPrice(visible: Boolean): String {
        return if (visible) {
            amount.toString()
        } else {
            "----"
        }
    }

    @Exclude
    val visibleSub = BehaviorSubject.createDefault<Boolean>(true)

    @Exclude
    fun getObservableString(): ObservableField<String> {
        return visibleSub.map { ObservableField(getDiaplsyedPrice(it)) }.blockingFirst()

    }
}