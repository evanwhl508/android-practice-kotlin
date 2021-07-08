package com.companyname.kotlinpractice.sample_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;

import com.companyname.kotlinpractice.R;

public class DemoAdapter extends BaseAdapter {

    String[] artists = new String[]{
            "aaa",
            "bbb",
            "ccc",
            "ddd",
            "eee",
            "fff",
            "ggg",
            "hhh",
            "iii",
            "aaa",
            "bbb",
            "ccc",
            "ddd",
            "eee",
            "fff",
            "ggg",
            "hhh",
            "iii",
    };

    private Context context;

    DemoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return artists.length;
    }

    @Override
    public Object getItem(int position) {
        return artists[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;

        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.layout_list_item, parent, false);
            vh = new ViewHolder();
            vh.name = view.findViewById(R.id.tv_name);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        String name = (String) getItem(position);
        vh.name.setText(name);
        return view;
    }

    class ViewHolder {
        TextView name;
    }
}
