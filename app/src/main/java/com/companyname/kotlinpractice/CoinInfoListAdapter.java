package com.companyname.kotlinpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CoinInfoListAdapter extends BaseAdapter {

    String[] exp;

    private Context context;

    CoinInfoListAdapter(Context context, String[] exp) {
        this.context = context;
        this.exp = exp;
    }

    @Override
    public int getCount() {
        return exp.length;
    }

    @Override
    public Object getItem(int position) {
        return exp[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;

        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.simple_list_item, parent, false);
            vh = new ViewHolder();
            vh.content = view.findViewById(R.id.tv_content);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        String content = (String) getItem(position);
        vh.content.setText(content);
        return view;
    }

    class ViewHolder {
        TextView content;
    }
}
