package com.companyname.kotlinpractice

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.kotlinpractice.databinding.PersonalInfoFragmentBinding
import com.companyname.kotlinpractice.entity.UserSpotBalance
import com.companyname.kotlinpractice.firestore.FirestoreManager
import com.companyname.kotlinpractice.ui.deposit.DepositActivity
import io.reactivex.rxjava3.core.Observable
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class PersonalInfoFragment : Fragment() {
    var coins = ArrayList<UserSpotBalance>()
    private lateinit var viewModel: PersonalInfoViewModel

    companion object {
        fun newInstance() = PersonalInfoFragment()
        var balanceList = arrayListOf<UserSpotBalance>()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pref = activity?.getSharedPreferences("currentAsset", Context.MODE_PRIVATE)
        val binding = DataBindingUtil.inflate<PersonalInfoFragmentBinding>(inflater, R.layout.personal_info_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PersonalInfoViewModel::class.java)

        binding.lifecycleOwner = this
        binding.model = viewModel
        val rvAdapter = PersonalInfoRVAdapter(coins)
        val username = "test"
        FirestoreManager.instance.db
                        .collection("users/$username/balance")
                        .addSnapshotListener { value, error ->
                            error?.let {
//                                Log.e("1111", it.toString())
                            } ?: run {
                                val bList = arrayListOf<UserSpotBalance>()
                                value?.documents?.mapNotNull { it.data }?.forEach { data ->
                            //                                    Log.e("balance data", "onCreateView: ${it.key} & ${it.value}", )
                                                                var itemValue = data["amount"]
                                                                if (itemValue is Long) {
                                                                    itemValue = itemValue.toDouble()
                                                                }
                                    Log.e("deposit", "data: $data", )
//                                    val b = Gson().fromJson(data.toString(),UserSpotBalance::class.java)
                                                                val b = UserSpotBalance(
                                                                    symbol = data["symbol"] as String,
                                                                    url = data["imgUrl"] as String,
                                                                    amount = itemValue as Double,
                                                                )
                                    bList.add(b)
                                }
                                rvAdapter.setCoins(bList)
                                balanceList = bList
                                viewModel.getCoins("USD").subscribe()
                            }
                        }
        val obsTimer: Observable<Long> = Observable.interval(0, 300, TimeUnit.SECONDS)

        obsTimer.flatMap { viewModel.getCoins("USD") }.subscribe()

        viewModel.ldCoin.observe(viewLifecycleOwner, {coins ->
//            Log.e("update balance", "onCreateView: ", )
            var totalBalance = 0.0F
            coins.forEach{ coin ->
                balanceList.forEach {
                    Log.e("update balance", "${coin.id} + ${it.symbol}" )
                    if (coin.id == it.symbol) {
                        totalBalance += it.amount.toFloat() * coin.price.toFloat()
                        it.lastPrice = it.currentPrice
                        it.currentPrice = coin.price
                    }
                }
//                viewModel.coinBalanceList.postValue(balanceList)
                rvAdapter.setCoins(balanceList)
            }
            pref?.edit()?.putFloat("total_balance", totalBalance)?.apply()
//            rvAdapter.setCoins(it)
        })

        viewModel.checkBox.observe(viewLifecycleOwner, {
                if (it) {
                    val totalBalance = pref?.getFloat("total_balance", 0.0F)
                    pref?.edit()?.putBoolean("is_asset_visible", it)?.apply()
                    binding.tvCurrentAssetAmount.text = totalBalance.toString()
                } else {
                    binding.tvCurrentAssetAmount.text = "***"
                }
        })

        pref?.let{viewModel.checkBox.postValue(pref.getBoolean("is_asset_visible", false))}

        binding.btnDeposit.setOnClickListener{
            DepositActivity.start(requireContext())
        }


        val rv = binding.rvCoinList
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this.context)
        val dec = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        dec.setDrawable(ColorDrawable(ContextCompat.getColor(this.requireContext(),R.color.black)))
        rv.addItemDecoration(dec)

        return binding.root
    }

}