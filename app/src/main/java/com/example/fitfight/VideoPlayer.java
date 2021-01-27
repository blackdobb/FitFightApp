package com.example.fitfight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends MainActivity {

    private VideoView videoView;
    private boolean afterPhoneCall;
    private MediaController mediaController;
    private TelephonyManager phoneManager;


    private int tag;

    private boolean videoStop;
    private int currentPosition;
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        tag = getIntent().getIntExtra("tag", 1);
        setContentView(R.layout.activity_test_video_player);
        findViewById();
        phoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneManager.listen(new MobliePhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);
        initView();
    }


    public void findViewById() {
        videoView = (VideoView) this.findViewById(R.id.player_test_vv);
    }

    public void initView() {
        loadVideo();
    }

    private void loadVideo() {
        String uri = "android.resource://" + getPackageName() + "/";

        switch (tag) {
            case 1:
                uri += R.raw.base;
                break;
            case 2:
                uri += R.raw.enhance;
                break;

        }
        videoView.setVideoURI(Uri.parse(uri));
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoStop = true;
                showInfo();
            }
        });
        videoView.start();
    }

    private void showInfo() {
        String message = "";
        switch (tag) {
            case 1:
                message = "FitFighter-Base ";
                break;
            case 2:
                message = "FitFighter-Enhance ";
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayer.this);
        builder.setTitle("Training finished!")
                .setMessage("Congratulations, " + message + " finished!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sp = getSharedPreferences("int",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        int du= sp.getInt("duration",0);
                        if(tag==1){
                            if(du < 8) {
                                editor.remove("duration");
                                editor.putInt("duration", 8);
                            }
                        }
                        if(tag==2){
                            if(du < 9) {
                                editor.remove("duration");
                                editor.putInt("duration", 9);
                            }
                        }
                        editor.apply();
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!videoStop) {
                videoView.pause();
                currentPosition = videoView.getCurrentPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayer.this);
                builder.setTitle("Buddy")
                        .setMessage("Are you sure to give up?")
                        .setPositiveButton("Fight later", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("I'm not gonna do that", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                videoView.seekTo(currentPosition);
                                videoView.start();
                            }
                        })
                        .create()
                        .show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    // 内部类
    private class MobliePhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (afterPhoneCall) {
                        currentPosition = videoView.getCurrentPosition();
                        videoView.seekTo(currentPosition);
                        videoView.resume();
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    videoView.pause();
                    currentPosition = videoView.getCurrentPosition();
                    afterPhoneCall = true;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    videoView.pause();
                    currentPosition = videoView.getCurrentPosition();
                    afterPhoneCall = true;
                    break;
                default:
                    break;
            }
        }
    }
}
