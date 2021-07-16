package com.companyname.kotlinpractice

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B: ViewDataBinding, VM: ViewModel>: AppCompatActivity() {
    lateinit var binding: B
    lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        viewModel = createViewModel()
        bindViewModel()
    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun createViewModel(): VM

    abstract fun bindViewModel()

    fun ArrayList<*>.test() {

    }
}