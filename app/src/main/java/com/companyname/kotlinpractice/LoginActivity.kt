@file:JvmName("RxTextView")
package com.companyname.kotlinpractice

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

import com.companyname.kotlinpractice.databinding.ActivityLoginBinding
import com.jakewharton.rxbinding4.InitialValueObservable
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlin.Unit

open class LoginActivity(): AppCompatActivity() {

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

        val obsAcc:InitialValueObservable<CharSequence> = acc.textChanges()
        val obsPwd:InitialValueObservable<CharSequence> = pwd.textChanges()
        val obsLoginBtn:Observable<Unit> = btnLogin.clicks()

        Observable.combineLatest(obsAcc, obsPwd, {acc1:CharSequence, pwd1:CharSequence -> acc1.length > 0 && pwd1.length > 0})
                .subscribe(btnLogin::setEnabled)

        Observable.combineLatest(obsAcc, obsLoginBtn, {charSequence, unit -> charSequence.toString()})
                .subscribe{
            val i = Intent(this, MainPageActivity::class.java)
            i.putExtra("username", it)
            startActivity(i)
        }

    }

    override fun onStop() {
        super.onStop()
        disposedBag.clear()
    }
}