@file:JvmName("RxTextView")
package com.companyname.kotlinpractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.companyname.kotlinpractice.databinding.ActivityLoginBinding
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.jakewharton.rxbinding4.InitialValueObservable
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.onesignal.OneSignal

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlin.Unit

open class LoginActivity(): AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    var disposedBag = CompositeDisposable()
    var pubA:PublishSubject<Int> = PublishSubject.create()
//    ReplaySubject<Integer> repA = ReplaySubject.create()
    var brhA:BehaviorSubject<Int> = BehaviorSubject.create()

    lateinit var binding: ActivityLoginBinding
    var viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this

        binding.model = viewModel
        viewModel.getCoins().subscribe()

        val acc:EditText = findViewById(R.id.et_name)
        val pwd:EditText = findViewById(R.id.et_pwd)
        val btnLogin:Button = findViewById(R.id.btn_login)

        btnLogin.setEnabled(false)

//        btnLogin.setOnClickListener {
//            Log.e("error", "onCreate: ", )
//            throw RuntimeException("Errrrrrror")
//        }
        auth = Firebase.auth

        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("sign in", "signInAnonymously:success")
                    auth.currentUser?.let{
                        uploadToken(it)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("sign in", "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

//        FirestoreManager.instance.db
//            .collection("testing")
//            .addSnapshotListener { value, error ->
//                error?.let {
//                    Log.e("1111", it.toString())
//                } ?: run {
//                    value?.let {
//                        it.documents.forEach { doc ->
//                            Log.e("2222", "" + doc.data.toString())
//                        }
//                    }
//                }
//
//            }


        val obsAcc:InitialValueObservable<CharSequence> = acc.textChanges()
        val obsPwd:InitialValueObservable<CharSequence> = pwd.textChanges()
        val obsLoginBtn:Observable<Unit> = btnLogin.clicks()

        Observable.combineLatest(obsAcc, obsPwd, {acc1:CharSequence, pwd1:CharSequence -> acc1.length > 0 && pwd1.length > 0})
                .subscribe(btnLogin::setEnabled)

        Observable.combineLatest(obsAcc, obsLoginBtn, {charSequence, unit -> charSequence.toString()})
                .subscribe{
            val pref = getSharedPreferences("firebase", Context.MODE_PRIVATE)
            pref.edit().putString("user", it).apply()
            val i = Intent(this, MainPageActivity::class.java)
            i.putExtra("username", it)
            startActivity(i)
        }

    }

    fun uploadToken(user: FirebaseUser) {
        OneSignal.getDeviceState()?.pushToken?.let {
            FirestoreManager.instance.db
                .document("push_token/${user.uid}")
                .set(HashMap<String, Any>().apply{
                    put("token", it)
                }, SetOptions.merge())
        }
    }

    override fun onStop() {
        super.onStop()
        disposedBag.clear()
    }
}