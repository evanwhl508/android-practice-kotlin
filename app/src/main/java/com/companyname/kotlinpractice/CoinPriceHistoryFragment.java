package com.companyname.kotlinpractice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * A fragment representing a list of Items.
 */
public class CoinPriceHistoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Coin coin;
    private CoinPriceHistory coinPriceHistory = new CoinPriceHistory();
    CompositeDisposable disposedBag = new CompositeDisposable();
    LineChart lineChart;
    CoinPriceHistoryRecyclerViewAdapter rv_adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CoinPriceHistoryFragment(Coin coin) {
        this.coin = coin;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CoinPriceHistoryFragment newInstance(int columnCount, Coin coin) {
        CoinPriceHistoryFragment fragment = new CoinPriceHistoryFragment(coin);
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
        View view = inflater.inflate(R.layout.fragment_coin_price_history_list, container, false);
        RecyclerView rv = view.findViewById(R.id.list);
        rv_adapter = new CoinPriceHistoryRecyclerViewAdapter(this.getContext(), coinPriceHistory);

        lineChart = view.findViewById(R.id.lineChart);
        initDataSet(); //設定數據源

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            rv.setLayoutManager(new LinearLayoutManager(context));
        } else {
            rv.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        getCoinPriceHistory(context, rv_adapter);
        rv.setAdapter(rv_adapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        disposedBag.clear();
    }

    private void getCoinPriceHistory(Context context, CoinPriceHistoryRecyclerViewAdapter rv_adapter) {
        Activity view = (Activity) context;
        Log.e("get Coins", "coin: " + coin.toString());
        Disposable d = CoinRepo.getCoinPriceHistory(coin.getId())
                .subscribe(coinPriceHistory1 -> {
                    Log.e("get Coins", "getData: " + coinPriceHistory1.toString());
                    coinPriceHistory = coinPriceHistory1;
                    rv_adapter.setmValues(coinPriceHistory1);
                    initDataSet();
                    initX();
                });
        disposedBag.add(d);
    }

    private ArrayList<Entry> getChartData(){
        final int DATA_COUNT = rv_adapter.getItemCount();
        ArrayList<Entry> chartData = new ArrayList<>();

        for(int i=0;i<DATA_COUNT;i++){
            List<Double> record_list = coinPriceHistory.getRecord().get(i);
            chartData.add(new Entry(record_list.get(0).floatValue(), record_list.get(1).floatValue()));
        }
        return chartData;
    }

//    private List<String> getLabels(){
//        List<String> chartLabels = new ArrayList<>();
//        for(int i=0;i<rv_adapter.getItemCount();i++){
//            chartLabels.add("X"+i);
//        }
//        return chartLabels;
//    }

    private void initDataSet() {
        final LineDataSet set;

        final ArrayList<Entry> values = getChartData();

        if (values.isEmpty()) {
            return;
        }
        // greenLine
        set = new LineDataSet(values, "");
        set.setMode(LineDataSet.Mode.LINEAR);//類型為折線
        set.setColor(R.color.green);//線的顏色
        set.setLineWidth(1.5f);//線寬
        set.setDrawCircles(false); //不顯示相應座標點的小圓圈(預設顯示)
        set.setDrawValues(true);//不顯示座標點對應Y軸的數字(預設顯示)
        set.setValueTextSize(8);//座標點數字大小
        set.setValueFormatter(new DefaultValueFormatter(1));//座標點數字的小數位數1位

        set.setDrawFilled(true);//使用範圍背景填充(預設不使用)

        set.setHighlightEnabled(false);//禁用點擊交點後顯示高亮線 (預設顯示，如為false則以下設定均無效)
        set.enableDashedHighlightLine(5, 5, 0);//高亮線以虛線顯示，可設定虛線長度、間距等
        set.setHighlightLineWidth(2);//高亮線寬度
        set.setHighLightColor(R.color.alert_red);//高亮線顏色

//理解爲多條線的集合
        LineData data = new LineData(set);
        lineChart.setData(data);//一定要放在最後
        lineChart.invalidate();//繪製圖表
    }

    private void initX() {
        XAxis xAxis = lineChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X軸標籤顯示位置(預設顯示在上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.setTextColor(R.color.black);//X軸標籤顏色
//        xAxis.setTextSize(12);//X軸標籤大小

        xAxis.setLabelCount(rv_adapter.getItemCount());//X軸標籤個數
//        xAxis.setSpaceMin(0.5f);//折線起點距離左側Y軸距離
//        xAxis.setSpaceMax(0.5f);//折線終點距離右側Y軸距離

        xAxis.setDrawGridLines(false);//不顯示每個座標點對應X軸的線 (預設顯示)

        //設定所需特定標籤資料
        String[] xValue = new String[]{"", "1/3", "1/10", "1/17", "1/24", "1/31", "2/7"};
        List<String> xList = new ArrayList<>();
        for (int i = 0; i < rv_adapter.getItemCount(); i++) {
            List<Double> record_list = coinPriceHistory.getRecord().get(i);
            xList.add(getDate(Math.round(record_list.get(0))));
//            xList.add(String.valueOf(i +1).concat("月"));
        }
        /**
         * 格式化軸標籤二種方式：
         * 1、用圖表庫已寫好的類_如下X 軸使用
         * 2、自己實現接口_下一步驟中Y 軸使用
         * */
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));

//        xAxis.setEnabled(false);//不顯示X軸 (預設顯示，如為false則以下設定均無效)
//        xAxis.setDrawAxisLine(false);//不顯示X軸的線 (預設顯示)
//        xAxis.setAxisLineColor(Color.GREEN);//X軸線顏色
//        xAxis.setAxisLineWidth(2f);//X軸線寬度
//
//        xAxis.setDrawLabels(false);//不顯示X軸的對應標籤 (預設顯示)
//        xAxis.setAxisMinimum(1);//X軸標籤最小值
//        xAxis.setAxisMaximum(10);//X軸標籤最大值
//        xAxis.setLabelRotationAngle(-25);//X軸數字旋轉角度
//
//        xAxis.enableGridDashedLine(5f, 5f, 0f); //格線以虛線顯示，可設定虛線長度、間距等，如setDrawGridLines(false)則此設定無效
//        xAxis.setGridLineWidth(2f);//格線寬度
//        xAxis.setGridColor(Color.RED);//格線顏色
    }

    private String getDate(long time) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Hong_Kong");
        Calendar cal = Calendar.getInstance(Locale.CHINESE);
        cal.setTimeZone(tz);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();
        return date;
    }
}