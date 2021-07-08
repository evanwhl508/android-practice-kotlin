package com.companyname.kotlinpractice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.companyname.kotlinpractice.databinding.FragmentCoinInfoBinding;
import com.companyname.kotlinpractice.retrofit.CoinService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class CoinInfoFragment extends Fragment {
    private Coin coin;
    CompositeDisposable disposedBag = new CompositeDisposable();
    FragmentCoinInfoBinding binding;

    public CoinInfoFragment(Coin coin) {
        this.coin = coin;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coin_info, container, false);
        Disposable d = CoinRepo.getCoinById(coin.getId()).subscribe(coinDetail -> {
            binding.setCoinDetail(coinDetail);
            upDateCoinDetail(binding.getRoot(), coinDetail);
        });
        disposedBag.add(d);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        disposedBag.clear();
    }

    private void upDateCoinDetail(View view, CoinDetail coinDetail) {
        if (coinDetail != null) {
            binding.lvExp.setAdapter(new CoinInfoListAdapter(view.getContext(), coinDetail.getExp().toArray(new String[0])));
        }
    }
}