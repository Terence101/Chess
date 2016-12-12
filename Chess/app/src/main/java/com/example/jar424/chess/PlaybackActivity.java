package com.example.jar424.chess;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import java.lang.reflect.Field;

import app.Game;
import app.Board;
import java.util.ArrayList;

public class PlaybackActivity extends AppCompatActivity {

    private static ImageButton[][] buttons;
    private Game game;
    private int curr;
    private ArrayList<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playback_activity);

        buttons = new ImageButton[8][8];

        boards = new ArrayList<Board>();
        initializeButtons();

        curr = 0;

        game = new Game();
        game.playback_Board();
        boards.add(game.getBoard());

    }

    private void initializeButtons(){
        int count = 1;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                String block = "block" + count;

                int id = getResources().getIdentifier(block, "id", this.getPackageName());

                buttons[i][j] = (ImageButton)findViewById(id);

                count++;
            }
        }
    }

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }


    public void prev (View v) {
        System.out.println("Previous Move Clicked");
    }

    public void next (View v) {
        System.out.println("Next Move Clicked");
    }

    public static ImageButton getButton(int row, int col){
        return buttons[row][col];
    }

}
