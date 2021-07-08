package com.companyname.kotlinpractice.sample_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.companyname.kotlinpractice.R;
import com.companyname.kotlinpractice.sample_code.TestFragment;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

//        FragmentContainerView fcv = findViewById(R.id.container);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TestFragment())
                .commitNow();
    }
}