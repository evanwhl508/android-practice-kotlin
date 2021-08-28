package com.companyname.kotlinpractice.ui.deposit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.companyname.kotlinpractice.BaseActivity
import com.companyname.kotlinpractice.PriceAlert
import com.companyname.kotlinpractice.R
import com.companyname.kotlinpractice.databinding.ActivityDepositBinding
import com.companyname.kotlinpractice.ext.asObservable
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.companyname.kotlinpractice.realm.RealmDeposit
import com.companyname.kotlinpractice.realm.RealmManager
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmResults
import io.realm.kotlin.where
import java.sql.Timestamp


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

        val pref = getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val username = pref?.getString("user", null)
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
//        res.addChangeListener { change ->
//            this.depositList.clear()
//            change.forEach {
//                this.depositList.add(it.amount)
//            }
//            val a = DepositListAdapter(
//                this.depositList.toTypedArray().reversedArray()
//            )
//            binding.lvDepositList.adapter = a
//        }

        FirestoreManager.instance.db
            .collection("users/$username/transaction/")
            .whereEqualTo("direction", "deposit")
            .addSnapshotListener { value, error ->
                error?.let {
                        Log.e("deposit list", it.toString())
                } ?: run {
                    this.depositList.clear()
                    value?.documents?.mapNotNull { it.data }?.forEach { data ->
                            this.depositList.add((data["amount"] as Double).toString())
                    }
                    val a = DepositListAdapter(
                        this.depositList.toTypedArray().reversedArray()
                    )
                    binding.lvDepositList.adapter = a
                }
            }

        binding.btnConfirm.setOnClickListener {
//            RealmManager.instance.realmDeposit.executeTransactionAsync {
//                val deposit = RealmDeposit()
//                deposit.id = System.currentTimeMillis().toString()
//                deposit.amount = binding.etAmount.text.toString()
//                it.insertOrUpdate(deposit)
//            }
            var currentBalance: Double = 0.0
            Log.e("deposit", "$currentBalance", )
            username?.let {
                Log.e("deposit", it, )

                FirestoreManager.instance.getSpotBalance(it).flatMap{ balanceList ->
                    Log.e("deposit", "$balanceList", )
                    balanceList?.forEach { c ->
                        if (c.id == "tether") {
                            currentBalance = c.get("amount") as Double
                        }
                    }
                    val adjAmount: Double = binding.etAmount.text.toString().toDouble()
                    FirestoreManager.instance.db
                        .document("users/$username/balance/tether/")
                        .set(HashMap<String, Any>().apply {
                            this["symbol"] = "tether"
                            this["amount"] = currentBalance + adjAmount
                            this["imgUrl"] = "https://static.coinstats.app/coins/TetherfopnG.png"
                                                          },
                            SetOptions.merge())
                        .asObservable { adjAmount }
                }
                .flatMap { adjAmount ->
                    val timestamp = Timestamp(System.currentTimeMillis()).time
                    val transData = mapOf<String, Any>(
                        "timestamp" to timestamp,
                        "price" to 1,
                        "amount" to adjAmount,
                        "direction" to "deposit",
                        "symbol" to "tether"
                    )
                    FirestoreManager.instance.db
                        .collection("users/$username/transaction/")
                        .add(transData)
                        .asObservable { true }
                }
                .subscribe{res ->
                    if (res) {
                            Toast.makeText(
                                this, "Success",
                                Toast.LENGTH_LONG
                            ).show()
                    }
                    else {
                        Toast.makeText(
                            this, "Fail",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
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