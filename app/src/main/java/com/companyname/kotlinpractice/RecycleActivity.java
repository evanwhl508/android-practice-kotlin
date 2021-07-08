package com.companyname.kotlinpractice;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.companyname.kotlinpractice.databinding.ActivityRecycleBinding;
import com.companyname.kotlinpractice.retrofit.CoinService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding4.widget.RxAdapterView;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.subjects.BehaviorSubject;


public class RecycleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    List<Coin> coins = new ArrayList<>();
    DemoRVAdapter rv_adapter = new DemoRVAdapter(this, coins);
    CompositeDisposable disposedBag = new CompositeDisposable();
    BehaviorSubject<String> obsCurrency = BehaviorSubject.create();

    ActivityRecycleBinding binding;
    CoinListViewModel viewModel = new CoinListViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycle);
        binding.setLifecycleOwner(this);
        binding.setModel(viewModel);

        binding.tvUsername.setText(getIntent().getStringExtra("username"));
//        viewModel.searchStr.observe(this, s -> {
//            Log.e("SUCCESS","SUCCESS LISTENING: " + s);
//            rv_adapter.setFilterStr(s);
////            rv_adapter.filterCoins(this.coins);
//        });
        viewModel.searchStr.observe(this, s -> viewModel.filterCoins());

        String[] dropdownItems = new String[]{"USD", "HKD", "EUR", "JPY", "GBP"};
        binding.spinCurrency.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdownItems));

        Observable<Long> obsTimer = Observable.interval(0, 60, TimeUnit.SECONDS);
        obsCurrency.onNext("USD");
        viewModel.curSpinnerPosition.observe(this, position -> {
            Log.e("spinner", "spinner: " + position );
            obsCurrency.onNext(dropdownItems[position]);
        });
//        Observable<Integer> obsSpinner = RxAdapterView.itemSelections(spinner);
        viewModel.getFilteredCoin().observe(this, coins -> {
            rv_adapter.setCoins(coins);
        });
        viewModel.ldCoin.observe(this, coinList -> {
            viewModel.filterCoins();
        });

        Disposable clD = Observable
                .combineLatest(obsTimer, obsCurrency, (aLong, string) -> string)
                .subscribe( s-> {
                    Log.e("spinner", "combineLatest: " + s );
                    viewModel.getCoins(s).subscribe();
                });

        // SwipeRefreshLayout
        SwipeRefreshLayout mSwipeRefreshLayout = binding.swipeContainer;
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView rv = binding.rvName;
        rv.setAdapter(rv_adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dec = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.black)));
        rv.addItemDecoration(dec);
        FloatingActionButton fab = binding.btnFab;
        fab.setOnClickListener(v -> rv.smoothScrollToPosition(0));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) { // scrolling down
                    new Handler().postDelayed(() -> fab.setVisibility(View.GONE), 2000); // delay of 2 seconds before hiding the fab

                } else if (dy < 0) { // scrolling up

                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // No scrolling
                    new Handler().postDelayed(() -> fab.setVisibility(View.GONE), 2000); // delay of 2 seconds before hiding the fab
                }

            }
        });

        disposedBag.add(clD);
//        disposedBag.add(obsSearch);

    }

    private void getData() {
        this.coins.clear();
        Disposable d = CoinRepo.getCoins(obsCurrency.getValue())
                .subscribe(coins -> {
                    Log.e("get Coins", "getData: " + coins.toString());
                    this.coins.addAll(coins);
                    rv_adapter.setCoins(coins);
                });
        Log.e("get Data", "getData: " + coins.toString());
        disposedBag.add(d);
    }

//    private void getData() {
//        new Thread() {
//
//            public void run() {
//                try {
//                    URL url = new URL("https://api.coinstats.app/public/v1/coins?skip=0&limit=100&currency=USD");
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                    urlConnection.connect();
////                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
////                    //writeToLog("&updateTime=" + URLEncoder.encode(lastUpdateTime, "UTF-8"));
////                    writer.write("?skip=0&limit=5&currency=USD");
////                    writer.flush();
//
//                    int status = urlConnection.getResponseCode();
//                    if (status == 200) {
//                        InputStream is = urlConnection.getInputStream();
//                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//                        String line = rd.readLine();
//
//                        JSONObject result = new JSONObject(line);
//                        JSONArray arr = result.getJSONArray("coins");
//                        int coinListSize = coins.size();
//                        for (int i=0 ; i < arr.length() ; i++) {
//                            Gson gson = new Gson();
//                            Coin coin = gson.fromJson(arr.getJSONObject(i).toString(), Coin.class);
////                            Coin coin = new Coin(arr.getJSONObject(i));
//                            if (coinListSize == 0) {
//                                coins.add(coin);
//                            }
//                            else {
//                                coins.setFilterStr(i, coin);
//                            }
//
//                        }
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                rv_adapter.setCoins(coins);
//                                rv_adapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                    else {
//                        Log.e("123213", "error?");
//                    }
//                } catch (MalformedURLException e) {
//                    Log.e("123213", "here1");
//                } catch (IOException e) {
//                    Log.e("123213", "here2 " + e.getLocalizedMessage());
//                } catch (JSONException e) {
//                    Log.e("123213", "here3");
//                }
//
//            }
//        }.start();
//    }

    @Override
    public void onRefresh() {
        getData();
        SwipeRefreshLayout swipeLayout = this.findViewById(R.id.swipe_container);
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposedBag.clear();
    }
}