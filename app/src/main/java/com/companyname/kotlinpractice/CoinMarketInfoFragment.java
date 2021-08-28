package com.companyname.kotlinpractice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.companyname.kotlinpractice.retrofit.CoinService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * A fragment representing a list of Items.
 */
public class CoinMarketInfoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Coin coin;
    private CoinPriceHistory coinPriceHistory = new CoinPriceHistory();
    List<CoinMarketInfo> market = new ArrayList<CoinMarketInfo>();
    CompositeDisposable disposedBag = new CompositeDisposable();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CoinMarketInfoFragment(Coin coin) {
        this.coin = coin;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CoinMarketInfoFragment newInstance(int columnCount, Coin coin) {
        CoinMarketInfoFragment fragment = new CoinMarketInfoFragment(coin);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_market_info_list, container, false);
        CoinMarketInfoRVAdapter rv_adapter = new CoinMarketInfoRVAdapter(market);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            getCoinMarketInfo(context, rv_adapter);
            recyclerView.setAdapter(rv_adapter);
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        disposedBag.clear();
    }

    private void getCoinMarketInfo(Context context, CoinMarketInfoRVAdapter rv_adapter) {
        Activity view = (Activity) context;
//        Log.e("get Coins", "coin: " + coin.toString());
        Disposable d = CoinRepo.getCoinMarket(coin.getId())
                .subscribe(markets1 -> {
//                    Log.e("get Coins", "getData: " + markets1.toString());
                    market = markets1;
                    rv_adapter.setMarket(markets1);
                });
        disposedBag.add(d);
    }
}