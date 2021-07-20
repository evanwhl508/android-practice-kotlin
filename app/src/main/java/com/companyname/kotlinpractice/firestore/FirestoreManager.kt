package com.companyname.kotlinpractice.firestore

import com.google.firebase.FirebaseApp.initializeApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreManager private constructor() {
    val db = Firebase.firestore

    companion object {
        val instance = FirestoreManager()
    }

    init {
    }
}