package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Field;


import app.Game;
import pieces.Piece;

public class PlayActivity extends AppCompatActivity {

    private static ImageButton[][] buttons;
    private Game game;
    private static String input;
    private ImageButton activeButton;
    private Piece activePiece;
    private boolean firstClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        buttons = new ImageButton[8][8];

        activeButton = null;
        activePiece = null;

        input = "";

        firstClick = true;

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

        ImageButton buttonPressed = (ImageButton) findViewById(v.getId());

        for(int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(buttons[row][col] == buttonPressed){

                    Piece p = game.getBoard().get(row, col);

                    if(firstClick){

                        if(p == null){
                            openDialog();
                        }else{
                            activePiece = p;
                            activeButton = buttonPressed;

                            firstClick = false;

                            GradientDrawable gd = new GradientDrawable();
                            gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
                            gd.setCornerRadius(5);
                            gd.setStroke(1, 0xFF000000);

                            activeButton.setBackgroundDrawable(gd);
                        }

                    }else{

                        //deselect piece
                        if(buttonPressed == activeButton){
                            activeButton.setBackgroundDrawable(null);
                            activePiece = null;
                            activeButton = null;
                            firstClick = true;
                            return;
                        }

                        if(activePiece.isValidMove(game.getBoard(), row, col)){
                            activePiece.move(game.getBoard(), row, col);

                            //change images
                            Integer pieceImage = (Integer) activeButton.getTag();
                            buttonPressed.setImageResource(pieceImage);
                            buttonPressed.setTag(pieceImage);

                            Integer square;

                            if(row % 2 == 0){
                                //even row
                                if(col % 2 == 0){
                                    //even col
                                    square = R.drawable.whitesquare;
                                }else{
                                    //odd col
                                    square = R.drawable.blacksquare;
                                }
                            }else{
                                //odd row
                                if(col % 2 == 0){
                                    //even col
                                    square = R.drawable.blacksquare;
                                }else{
                                    //odd col
                                    square = R.drawable.whitesquare;
                                }
                            }

                            activeButton.setImageResource(square);
                            activeButton.setTag(square);

                            activeButton.setBackgroundDrawable(null);

                            activePiece = null;
                            activeButton = null;

                            firstClick = true;

                        }else{
                            openDialog();
                        }

                    }

                    return;

                }
            }
        }





        //buttonPressed.setImageResource(R.drawable.blackking);
    }

    private void openDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage("Illegal");


        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



    public static ImageButton getButton(int row, int col){
        return buttons[row][col];
    }

}
