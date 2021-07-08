package com.companyname.kotlinpractice.sample_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.companyname.kotlinpractice.R;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView lv = findViewById(R.id.listview);
        lv.setAdapter(new DemoAdapter(this));
    }
}