package com.companyname.kotlinpractice.sample_code;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.companyname.kotlinpractice.R;
import com.companyname.kotlinpractice.MainPageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button other = findViewById(R.id.btn_other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainPageActivity.class);
                i.putExtra("name", "Test");
                Pair<View, String> pair1 = new Pair(findViewById(R.id.tv_main), "tv_test");
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(MainActivity.this, pair1);
                startActivityForResult(i, 1, options.toBundle());
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//                startActivity(browserIntent);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Success Result OK", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "CANCELED Result Not OK", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}