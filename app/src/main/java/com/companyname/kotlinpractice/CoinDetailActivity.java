package com.companyname.kotlinpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.companyname.kotlinpractice.databinding.ActivityCoinDetailBinding;
import com.companyname.kotlinpractice.firestore.FirestoreManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CoinDetailActivity extends FragmentActivity {
    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager2;
    private Coin coin;
    ActivityCoinDetailBinding binding;

    static public void start(View v, Coin coin, TextView name, ImageView icon) {
        Intent intent = new Intent(v.getContext(), CoinDetailActivity.class);
        intent.putExtra("coin", coin);
        String symbol = coin.getSymbol();
        String name_transition = "title_" + symbol;
        String icon_transition = "icon_" + symbol;
        name.setTransitionName(name_transition);
        icon.setTransitionName(icon_transition);
        Pair name_pair = new Pair(name, name_transition);
        Pair<View, String> icon_pair = new Pair(icon, icon_transition);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation((Activity) v.getContext(), name_pair, icon_pair);
        v.getContext().startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coin_detail);
        binding.setLifecycleOwner(this);
//        setContentView(R.layout.activity_coin_detail);
        TabLayout tabLayout = binding.layoutTabs;
        viewPager2 = binding.pager2;
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        TextView tv_name = binding.tvName;
        ImageView iv_icon = binding.ivIcon;

        coin = (Coin) getIntent().getSerializableExtra("coin");
        String symbol = coin.getSymbol();
        tv_name.setTransitionName("title_" + symbol);
        iv_icon.setTransitionName("icon_" + symbol);

        tv_name.setText(coin.getName());
        Glide.with(this).load(coin.getIcon()).into(iv_icon);

        SharedPreferences pref = getSharedPreferences("firebase", Context.MODE_PRIVATE);
        String username = pref.getString("user", "error");
//        Log.e("username", "onCreate: " + username );

        binding.btnBuy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                TODO: popup dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CoinDetailActivity.this);
                builder.setTitle("Buy Coin");
// Set up the input
                final EditText input = new EditText(CoinDetailActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                      TODO: Call Firebase BUY function
                        String amount = input.getText().toString();
                        Map<String, Object> data = new HashMap<>();
                        data.put("username", username);
                        data.put("pair", coin.getId());
                        data.put("amount", Float.parseFloat(amount));
                        data.put("price", Float.parseFloat(coin.getFormattedPrice()));
                        data.put("imgUrl", coin.getIcon());
//                        Log.e("data", "onClick: " + data.toString());
                        FirebaseFunctions.getInstance() // Optional region: .getInstance("europe-west1")
                                .getHttpsCallable("buyCoin")
                                .call(data)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                                    @Override
                                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                                        Toast.makeText(
                                                v.getContext(), "Success",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        binding.btnSell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                TODO: Call Firebase SELL function
                AlertDialog.Builder builder = new AlertDialog.Builder(CoinDetailActivity.this);
                builder.setTitle("Sell Coin");
// Set up the input
                final EditText input = new EditText(CoinDetailActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                      TODO: Call Firebase BUY function
                        String amount = input.getText().toString();
                        Map<String, Object> data = new HashMap<>();
                        data.put("username", username);
                        data.put("pair", coin.getId());
                        data.put("amount", Float.parseFloat(amount));
                        data.put("price", Float.parseFloat(coin.getFormattedPrice()));
//                        Log.e("data", "onClick: " + data.toString());
                        FirebaseFunctions.getInstance() // Optional region: .getInstance("europe-west1")
                                .getHttpsCallable("sellCoin")
                                .call(data)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                                    @Override
                                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                                        Toast.makeText(
                                                v.getContext(), "Success",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                        Log.e("tab name", "position: " + position);
                        switch (position) {
                            case 0:
                                tab.setText("Info");
                                break;
                            case 1:
                                tab.setText("Price History");
                                break;
                            case 2:
                                tab.setText("Market Price");
                                break;
                            default:
                                tab.setText("New Tab " + (position + 1));
                        }
                    }
                }).attach();
    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CoinInfoFragment(coin);
                case 1:
                    return new CoinPriceHistoryFragment(coin);
                case 2:
                    return new CoinMarketInfoFragment(coin);
                default:
                    return new CoinInfoFragment(coin);
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}