package com.example.jar424.chess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.Game;

public class PlayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        Game game = new Game();
        //game.start();

    }


}
