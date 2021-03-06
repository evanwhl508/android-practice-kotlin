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
        initDataSet(); //???????????????

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
//        Log.e("get Coins", "coin: " + coin.toString());
        Disposable d = CoinRepo.getCoinPriceHistory(coin.getId())
                .subscribe(coinPriceHistory1 -> {
//                    Log.e("get Coins", "getData: " + coinPriceHistory1.toString());
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
        set.setMode(LineDataSet.Mode.LINEAR);//???????????????
        set.setColor(R.color.green);//????????????
        set.setLineWidth(1.5f);//??????
        set.setDrawCircles(false); //????????????????????????????????????(????????????)
        set.setDrawValues(true);//????????????????????????Y????????????(????????????)
        set.setValueTextSize(8);//?????????????????????
        set.setValueFormatter(new DefaultValueFormatter(1));//??????????????????????????????1???

        set.setDrawFilled(true);//????????????????????????(???????????????)

        set.setHighlightEnabled(false);//???????????????????????????????????? (?????????????????????false????????????????????????)
        set.enableDashedHighlightLine(5, 5, 0);//????????????????????????????????????????????????????????????
        set.setHighlightLineWidth(2);//???????????????
        set.setHighLightColor(R.color.alert_red);//???????????????

//???????????????????????????
        LineData data = new LineData(set);
        lineChart.setData(data);//?????????????????????
        lineChart.invalidate();//????????????
    }

    private void initX() {
        XAxis xAxis = lineChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X?????????????????????(???????????????????????????????????????/??????????????????/???????????????????????????)
        xAxis.setTextColor(R.color.black);//X???????????????
//        xAxis.setTextSize(12);//X???????????????

        xAxis.setLabelCount(rv_adapter.getItemCount());//X???????????????
//        xAxis.setSpaceMin(0.5f);//????????????????????????Y?????????
//        xAxis.setSpaceMax(0.5f);//????????????????????????Y?????????

        xAxis.setDrawGridLines(false);//??????????????????????????????X????????? (????????????)

        //??????????????????????????????
        String[] xValue = new String[]{"", "1/3", "1/10", "1/17", "1/24", "1/31", "2/7"};
        List<String> xList = new ArrayList<>();
        for (int i = 0; i < rv_adapter.getItemCount(); i++) {
            List<Double> record_list = coinPriceHistory.getRecord().get(i);
            xList.add(getDate(Math.round(record_list.get(0))));
//            xList.add(String.valueOf(i +1).concat("???"));
        }
        /**
         * ?????????????????????????????????
         * 1??????????????????????????????_??????X ?????????
         * 2?????????????????????_???????????????Y ?????????
         * */
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));

//        xAxis.setEnabled(false);//?????????X??? (?????????????????????false????????????????????????)
//        xAxis.setDrawAxisLine(false);//?????????X????????? (????????????)
//        xAxis.setAxisLineColor(Color.GREEN);//X????????????
//        xAxis.setAxisLineWidth(2f);//X????????????
//
//        xAxis.setDrawLabels(false);//?????????X?????????????????? (????????????)
//        xAxis.setAxisMinimum(1);//X??????????????????
//        xAxis.setAxisMaximum(10);//X??????????????????
//        xAxis.setLabelRotationAngle(-25);//X?????????????????????
//
//        xAxis.enableGridDashedLine(5f, 5f, 0f); //???????????????????????????????????????????????????????????????setDrawGridLines(false)??????????????????
//        xAxis.setGridLineWidth(2f);//????????????
//        xAxis.setGridColor(Color.RED);//????????????
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