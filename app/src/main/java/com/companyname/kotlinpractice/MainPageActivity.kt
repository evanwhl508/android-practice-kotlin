package com.companyname.kotlinpractice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainPageActivity : FragmentActivity() {
//    lateinit var binding: ActivityMainPageBinding
    val NUM_PAGES = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
//        binding.lifecycleOwner = this
        val tv = findViewById<TextView>(R.id.tv_test)
        val tabLayout: TabLayout = findViewById(R.id.layout_tabs)
        val viewPager: ViewPager2 = findViewById(R.id.pager2)
        val pagerAdapter: FragmentStateAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab, position ->
            Log.e("tab name", "position: $position")
            when (position) {
                0 -> tab.text = "Price List"
                1 -> tab.text = "Personal Info"
                2 -> tab.text = "Price Alert"
                else -> tab.text = "New Tab " + (position + 1)
            }
        }.attach()
    }

    private class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RecycleActivity()
                1 -> PersonalInfoFragment()
                2 -> PriceAlertFragment()
                else -> RecycleActivity()
            }
        }

        override fun getItemCount(): Int {
            return 3
        }
    }
}