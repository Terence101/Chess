package com.example.jar424.chess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import java.lang.reflect.Field;


import app.Game;
import pieces.Piece;

public class PlayActivity extends AppCompatActivity {

    private static ImageButton[][] buttons;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        buttons = new ImageButton[8][8];

        initializeButtons();


        game = new Game();
        game.drawBoard();

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

    public void clicked(View v){
        System.out.println("clicked");

        //buttons[5][5].setImageResource(R.drawable.blackking);
        //ImageButton buttonPressed = (ImageButton) findViewById(v.getId());



        //buttonPressed.setImageResource(R.drawable.blackking);
    }



    public static ImageButton getButton(int row, int col){
        return buttons[row][col];
    }

}
