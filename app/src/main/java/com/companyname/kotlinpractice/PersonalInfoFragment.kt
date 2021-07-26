package com.companyname.kotlinpractice

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.kotlinpractice.databinding.PersonalInfoFragmentBinding
import com.companyname.kotlinpractice.ui.deposit.DepositActivity
import java.util.ArrayList

class PersonalInfoFragment : Fragment() {
    var coins = ArrayList<Coin>()
    private lateinit var viewModel: PersonalInfoViewModel

    companion object {
        fun newInstance() = PersonalInfoFragment()
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
        viewModel.getCoins("USD").subscribe()
        viewModel.ldCoin.observe(viewLifecycleOwner, {
            rvAdapter.setCoins(it)
        })
        viewModel.checkBox.observe(viewLifecycleOwner, {
                pref?.edit()?.putBoolean("is_asset_visible", it)?.apply()
                if (it) {
                    binding.tvCurrentAssetAmount.text = "1234567890"
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