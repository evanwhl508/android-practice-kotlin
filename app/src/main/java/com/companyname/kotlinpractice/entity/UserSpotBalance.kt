package com.companyname.kotlinpractice.entity

import androidx.databinding.ObservableField
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.subjects.BehaviorSubject

class UserSpotBalance(
    @SerializedName("symbol") var symbol: String,
    @SerializedName("amount") var amount: Double = 0.0,
    @SerializedName("imgUrl") var url: String,
    var lastPrice:Double = 0.0,
    var currentPrice:Double = 0.0,
) {

    fun getFormattedAmount(): String {
        return amount.toString()
    }

    private fun getDisplayedPrice(visible: Boolean): String {
        return if (visible) {
            amount.toString()
        } else {
            "----"
        }
    }

    private val visibleSub: BehaviorSubject<Boolean> = BehaviorSubject.createDefault<Boolean>(true)

    fun getObservableString(): ObservableField<String> {
        return visibleSub.map { ObservableField(getDisplayedPrice(it)) }.blockingFirst()

    }

    fun getTotalValue(): String {
        return String.format("%.2f", amount * currentPrice)
    }

    fun getTotalValueColor(): String {
        return when {
            currentPrice > lastPrice -> {
                "+"
            }
            currentPrice < lastPrice -> {
                "-"
            }
            else -> {
                ""
            }
        }
    }
}