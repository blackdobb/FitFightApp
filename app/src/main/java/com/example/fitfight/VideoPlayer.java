package com.example.fitfight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends MainActivity {

    private VideoView videoView;

    private MediaController mediaController;


    private int tag;

    private boolean videoStop;
    private int currentPosition;
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        tag = getIntent().getIntExtra("tag", 1);
        setContentView(R.layout.activity_test_video_player);
        findViewById();
        initView();
    }


    public void findViewById() {
        videoView = (VideoView) this.findViewById(R.id.player_test_vv);
    }

    public void initView() {
        loadVideo();
    }

    private void loadVideo() {
        AlertDialog.Builder reminder = new AlertDialog.Builder(VideoPlayer.this);
        reminder.setTitle("Level limited!")
                .setMessage("Please complete the pre-training")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        String uri = "android.resource://" + getPackageName() + "/";
        SharedPreferences sp = getSharedPreferences("int",MODE_PRIVATE);
        int duration = sp.getInt("duration",0);

        switch (tag) {
            case 1:
                uri += R.raw.base;
                break;
            case 2:
                if (duration>=8) {
                    uri += R.raw.enhance;
                    break; }
                else{
                    reminder.create()
                            .show();
                    return; }
            case 3:
                if (duration>= 9) {
                    uri += R.raw.acme;
                    break; }
                else{
                    reminder.create()
                            .show();
                    return; }
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
            case 3:
                message = "FitFighter-Acme ";
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
                            if(du <= 8) {
                                editor.remove("duration");
                                editor.putInt("duration", 8);
                            }
                        }
                        if(tag==2){
                            if(du <= 9) {
                                editor.remove("duration");
                                editor.putInt("duration", 9);
                            }
                        }
                        if(tag==3){
                            if(du <= 10) {
                                editor.remove("duration");
                                editor.putInt("duration", 10);
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
}
