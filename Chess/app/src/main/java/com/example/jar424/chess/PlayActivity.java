package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    private TextView turnBox;
    private TextView message;
    private Button undo;
    private Button ai;
    private Button resign;
    private Button draw;

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

        turnBox = (TextView) findViewById(R.id.turnBox);
        message = (TextView) findViewById(R.id.message);
        undo = (Button) findViewById(R.id.undo);
        ai = (Button) findViewById(R.id.ai);
        resign = (Button) findViewById(R.id.resign);
        draw = (Button) findViewById(R.id.draw);

        game = new Game();
        game.drawBoard();

        //start();

    }

    private void start(){

        while(game.isGameOn()){
            boolean firstTry = true;

            game.drawBoard();

            do{

                input = "";

                if(!firstTry)
                    message.setText("Illegal move");

                if(game.isWhiteTurn()){
                    while(input.split("\\s+").length < 2){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //do nothing
                    }


                }else{

                    while(input.split("\\s+").length < 2){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //do nothing
                    }

                }

                firstTry = false;

            }while(!game.takeTurn(input));

            game.setWhiteTurn(!game.isWhiteTurn());

            swapTurnBox();
        }

        /*
        public void start(){

            String input;

            while(gameOn){

                boolean firstTry = true;

                drawBoard();

                do{

                    if(!firstTry)
                        System.out.println("\nIllegal move, try again\n");

                    if(whiteTurn){
                        if (white.isDefeated()) {
                            System.out.println("Checkmate");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("\nBlack Wins");

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        }

                        if(white.isChecked()){
                            System.out.println("Check\n");
                            white.setChecked(false);
                        }
                        System.out.print("White's move: ");
                    }else{
                        if(black.isDefeated()){
                            System.out.println("Checkmate");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("\nWhite Wins");

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        }
                        if(black.isChecked()){
                            System.out.println("Check\n");
                            black.setChecked(false);
                        }
                        System.out.print("Black's move: ");
                    }

                    input = "";

                    firstTry = false;

                }while(!takeTurn(input));

                whiteTurn = !whiteTurn;



            }

        }
        */

    }

    private void swapTurnBox() {
        if(game.isWhiteTurn()) {
            turnBox.setBackgroundResource(R.drawable.border);
        }else {
            turnBox.setBackgroundColor(Color.BLACK);
        }
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
        //System.out.println("clicked");

        ImageButton buttonPressed = (ImageButton) findViewById(v.getId());

        for(int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if(buttons[7 - row][col] == buttonPressed){

                    Piece p = game.getBoard().get(row, col);

                    if(p != null)
                        System.out.println(p.getName() + "" + p.isWhite());

                    if(firstClick){

                        input = "";

                        if(p == null || (p.isWhite() != game.isWhiteTurn())){
                            openDialog();
                        }else {
                            activePiece = p;
                            activeButton = buttonPressed;

                            firstClick = false;

                            activeButton.setColorFilter(Color.BLUE);

                            input = input + (char) (col + 97);
                            input = input + (char) (row + 49) + " ";
                            System.out.println("input: " + input);

                        }

                    }else{

                        //deselect piece
                        if(buttonPressed == activeButton){
                            activeButton.setColorFilter(null);
                            activePiece = null;
                            activeButton = null;
                            firstClick = true;

                            input = "";

                            return;
                        }

                        input = input + (char)(col + 97);
                        input = input + (char) (row + 49) + " ";
                        System.out.println("input: " + input);

                        if(game.takeTurn(input)){

                            game.drawBoard();
                            game.setWhiteTurn(!game.isWhiteTurn());
                            swapTurnBox();

                            /*
                            //change images
                            Integer pieceImage = (Integer) activeButton.getTag();
                            buttonPressed.setImageResource(pieceImage);
                            buttonPressed.setTag(pieceImage);
                            */

                            activeButton.setColorFilter(null);

                            activePiece = null;
                            activeButton = null;

                            firstClick = true;

                        }else{
                            input = input.substring(0, input.indexOf(" ") + 1);
                            openDialog();
                        }

                    }

                    return;

                }
            }
        }





        //buttonPressed.setImageResource(R.drawable.blackking);
    }

    public void functionClicked(View v){

        Button buttonPressed = (Button) findViewById(v.getId());

        if(buttonPressed == undo){
            message.setText("undo");
        }

        if(buttonPressed == ai){
            message.setText("ai");
        }

        if(buttonPressed == resign){
            message.setText("resign");
        }

        if(buttonPressed == draw){
            message.setText("draw");
        }

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
