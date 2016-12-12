package com.example.jar424.chess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

import app.Game;

public class RecordActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);

    }

    public void playback (View v) {
        Intent intent = new Intent(this, PlaybackActivity.class);
        startActivity(intent);
    }

}
