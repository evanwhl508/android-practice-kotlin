package com.companyname.kotlinpractice.ui.deposit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.companyname.kotlinpractice.BaseActivity
import com.companyname.kotlinpractice.R
import com.companyname.kotlinpractice.databinding.ActivityDepositBinding
import com.companyname.kotlinpractice.realm.RealmDeposit
import com.companyname.kotlinpractice.realm.RealmManager
import io.realm.RealmResults
import io.realm.kotlin.where


class DepositActivity : BaseActivity<ActivityDepositBinding, DepositViewModel>() {
    lateinit var res: RealmResults<RealmDeposit>
    var depositList: ArrayList<String> = arrayListOf<String>()
    lateinit var adapter: DepositListAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DepositActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ***Realm***
        res = RealmManager.instance.realmDeposit.where<RealmDeposit>().findAllAsync()
//        res.forEach {
//            depositList.add(it.amount)
//        }
        Log.e("1111111", "onCreate: " + depositList.toString())

        adapter =
        DepositListAdapter(
            this.depositList.toTypedArray().reversedArray()
        )
        res.addChangeListener { change ->
            this.depositList.clear()
            change.forEach {
                this.depositList.add(it.amount)
            }
            val a = DepositListAdapter(
                this.depositList.toTypedArray().reversedArray()
            )
            binding.lvDepositList.setAdapter(a)
        }
        binding.btnConfirm.setOnClickListener {
            RealmManager.instance.realmDeposit.executeTransactionAsync {
                val deposit = RealmDeposit()
                deposit.id = System.currentTimeMillis().toString()
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

//    override fun onResume() {
//        super.onResume()
//
//        adapter =
//            DepositListAdapter(
//                arrayOf("123","456")
//            )
//        binding.lvDepositList.setAdapter(adapter)
//    }
}