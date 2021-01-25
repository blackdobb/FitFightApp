package com.example.fitfight;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
        AlertDialog.Builder main_reminder = new AlertDialog.Builder(MainActivity.this);
        main_reminder.setTitle("Level limited!")
                .setMessage("Please complete the pre-training")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        Intent intent = new Intent(this, VideoPlayer.class);
        Intent webintent = new Intent(this, WebActivity.class);
        SharedPreferences sp = getSharedPreferences("int",MODE_PRIVATE);
        int duration = sp.getInt("duration",0);


        switch (v.getId()) {
            case R.id.train_base:
                intent.putExtra("tag", 1);
                startActivity(intent);
                break;
            case R.id.train_enhance:
                if (duration>= 8) {
                    intent.putExtra("tag", 2);
                    startActivity(intent);
                    break; }
                else{
                    main_reminder.create()
                            .show();
                    return; }
            case R.id.train_acme:
                if (duration>= 9) {
                    startActivity(webintent);
                    break; }
                else{
                    main_reminder.create()
                            .show();
                    return; }
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
