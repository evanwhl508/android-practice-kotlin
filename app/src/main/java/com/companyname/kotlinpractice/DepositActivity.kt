package com.companyname.kotlinpractice

import android.R.layout
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.companyname.kotlinpractice.databinding.ActivityDepositBinding
import com.companyname.kotlinpractice.realm.RealmDeposit
import com.companyname.kotlinpractice.realm.RealmManager
import io.realm.RealmResults
import io.realm.kotlin.where


class DepositActivity : BaseActivity<ActivityDepositBinding, DepositViewModel>() {
    lateinit var res: RealmResults<RealmDeposit>
    var depositList: ArrayList<String> = arrayListOf<String>()

    companion object {
        fun start(v: View) {
            val intent = Intent(v.context, DepositActivity::class.java)
            v.context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ***Realm***
        res = RealmManager.instance.realmDeposit.where<RealmDeposit>().findAllAsync()
        res?.forEach {
            depositList.add(it.amount)
        }
        val adapter =
        DepositListAdapter(
            this,
            this.depositList.toTypedArray()
        )
        binding.lvDepositList.setAdapter(adapter)


        res.addChangeListener { change ->
            change.forEach {
                Log.e("deposit", "" + it)
                this.depositList.add(it.amount)
                val a = DepositListAdapter(
                    this,
                    this.depositList.toTypedArray()
                )
                binding.lvDepositList.setAdapter(a)
            }
        }
        binding.btnConfirm.setOnClickListener {
            RealmManager.instance.realmDeposit.executeTransactionAsync {
                val deposit = RealmDeposit()
                deposit.amount = binding.etAmount.text.toString()
                it.insertOrUpdate(deposit)
            }
        }
    }

    override val layoutId: Int = R.layout.activity_deposit

    override fun createViewModel() = DepositViewModel()

    override fun bindViewModel() {
        binding.model = viewModel
    }
}