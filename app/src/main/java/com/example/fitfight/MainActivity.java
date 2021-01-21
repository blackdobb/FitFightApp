package com.example.fitfight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout baseTraning;
    private LinearLayout enhanceTraning;
    private LinearLayout acmeTraning;


    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
		setContentView(R.layout.activity_main);
        main_findViewById();
        main_initView();
    }

    public void main_findViewById() {
        baseTraning = (LinearLayout) findViewById(R.id.train_base);
        enhanceTraning = (LinearLayout) findViewById(R.id.train_enhance);
        acmeTraning = (LinearLayout) findViewById(R.id.train_acme);
    }

    public void main_initView() {
        baseTraning.setOnClickListener(this);
        enhanceTraning.setOnClickListener(this);
        acmeTraning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, VideoPlayer.class);
        switch (v.getId()) {
            case R.id.train_base:
                intent.putExtra("tag", 1);
                startActivity(intent);
                break;
            case R.id.train_enhance:
                intent.putExtra("tag", 2);
                startActivity(intent);
                break;
            case R.id.train_acme:
                intent.putExtra("tag", 3);
                startActivity(intent);
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }
}
