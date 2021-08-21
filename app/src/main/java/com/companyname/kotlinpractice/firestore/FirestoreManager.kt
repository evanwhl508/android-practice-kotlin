package com.companyname.kotlinpractice.firestore

import android.R.attr
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.companyname.kotlinpractice.PriceAlert
import com.companyname.kotlinpractice.ext.asObservable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable


class FirestoreManager private constructor() {
    val db = Firebase.firestore
    val functionInstance = FirebaseFunctions.getInstance()

    companion object {
        val instance = FirestoreManager()
    }

    init {
    }

    fun deleteFavCoin(context:Context, coinId: String) {
        val pref = context!!.getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val uid = pref?.getString("user", null)
            uid?.let { id ->
                instance.db
                    .collection("price_alert")
                    .document(id)
                    .collection("coins")
                    .document(coinId)
                    .delete()
                    .asObservable { true }
        }
    }

    fun buySpot(context: Context, coinId: String, amount: Float, price: Float) {
        val pref = context.getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val username = pref?.getString("user", null)
        username?.let { u ->
            val data: MutableMap<String, Any> = HashMap()
            data["username"] = u
            data["pair"] = coinId
            data["amount"] = amount
            data["price"] = price
            functionInstance // Optional region: .getInstance("europe-west1")
                .getHttpsCallable("buyCoin")
                .call(data)
                .addOnFailureListener {
                    Log.wtf("FF", it)
                }
                .addOnSuccessListener {
                    Toast.makeText(
                        context, it.data.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } }
    }
}