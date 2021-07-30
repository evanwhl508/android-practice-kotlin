package com.companyname.kotlinpractice

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.companyname.kotlinpractice.databinding.ActivityRecycleBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit


class TestRecyclerActivity() : AppCompatActivity() {
    var coins = ArrayList<Coin>()
    private var rvAdapter = DemoRVAdapter()
    val disposedBag = CompositeDisposable()
    private var obsCurrency:BehaviorSubject<String> = BehaviorSubject.create()
    private val viewModel = CoinListViewModel()
    lateinit var binding: ActivityRecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycle)
        binding.lifecycleOwner = this
        binding.model = viewModel

//        binding.tvUsername.setText(getIntent().getStringExtra("username"))
        viewModel.searchStr.observe(this, {viewModel.filterCoins()})

        val dropdownItems = arrayOf("USD", "HKD", "EUR", "JPY", "GBP")
        binding.spinCurrency.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dropdownItems)

        val obsTimer:Observable<Long> = Observable.interval(0, 60, TimeUnit.SECONDS)
        obsCurrency.onNext("USD")
        viewModel.curSpinnerPosition.observe(this, {obsCurrency.onNext(dropdownItems[it])})
        viewModel.filteredCoin.observe(this, {rvAdapter.setCoins(it)})
        viewModel.ldCoin.observe(this, {viewModel.filterCoins()})

        val clD:Disposable = Observable
            .combineLatest(obsTimer, obsCurrency, { aLong, string -> string })
            .subscribe { id -> viewModel.getCoins(id).subscribe() }

        // SwipeRefreshLayout
//        val mSwipeRefreshLayout = binding.swipeContainer
//        mSwipeRefreshLayout.setOnRefreshListener(this)

        val rv = binding.rvName
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)
        val dec = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dec.setDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.black)))
        rv.addItemDecoration(dec)
//        val fab = binding.btnFab
//        fab.setOnClickListener { rv.smoothScrollToPosition(0) }

//        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0) { // scrolling down
//                    Handler(Looper.getMainLooper()).postDelayed({ fab.visibility = View.GONE }, 2000) // delay of 2 seconds before hiding the fab
//
//                } else if (dy < 0) { // scrolling up
//                    fab.visibility = View.VISIBLE
//                }
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // No scrolling
//                    Handler(Looper.getMainLooper()).postDelayed({ fab.visibility = View.GONE }, 2000) // delay of 2 seconds before hiding the fab
//                }
//            }
//        })

        disposedBag.add(clD)
//        return binding.root

    }

//    private fun getData() {
//        this.coins.clear()
//        val d:Disposable = CoinRepo.getCoins(obsCurrency.getValue())
//                .subscribe {
//                    this.coins.addAll(it)
//                    rvAdapter.setCoins(it)
//                }
//        Log.e("get Data", "getData: " + coins.toString())
//        disposedBag.add(d)
//    }

//    override fun onRefresh() {
//        getData()
//        val swipeLayout = binding.swipeContainer
//        if (swipeLayout.isRefreshing) {
//            swipeLayout.isRefreshing = false
//        }
//    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }

    override fun onStop() {
        super.onStop()
        disposedBag.clear()
    }
}