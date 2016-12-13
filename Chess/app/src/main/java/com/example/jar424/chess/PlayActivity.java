package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import app.Board;
import app.Game;
import app.Player;
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
    private ArrayList<Game> games;

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

        games = new ArrayList<Game>();

        game = new Game();
        game.drawBoard();
        games.add(new Game(game));

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
                            illegalDialog();
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

                            games.add(new Game(game));
                            System.out.println(games.size());

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
                            illegalDialog();
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
            //message.setText("undo");
            undo();
        }

        if(buttonPressed == ai){
            //message.setText("ai");
            ai();
        }

        if(buttonPressed == resign){
            //message.setText("resign");
            resign();
        }

        if(buttonPressed == draw){
            message.setText("draw");
        }

    }

    private void undo(){

        if(games.size() < 2){
            //can't undo
            return;
        }

        games.remove(game);

        game = games.get(games.size() - 1);

        game.drawBoard();
        swapTurnBox();
        System.out.println(games.size());


    }

    private void resign(){

        if(game.isWhiteTurn()){
            game.getWhite().setDefeated(true);
        }else{
            game.getBlack().setDefeated(true);
        }

        endDialog();


    }

    private void ai(){

        Player p;
        Random random = new Random();
        List<Piece> pieces;
        Piece piece;

        if(game.isWhiteTurn()){
            p = game.getWhite();
        }else{
            p = game.getBlack();
        }

        pieces = p.getPieces();



        do{

            input = "";

            //get random piece
            int x = random.nextInt(pieces.size());
            piece = pieces.get(x);

            input = input + (char) (piece.getCol() + 97);
            input = input + (char) (piece.getRow() + 49) + " ";

            //try random spot
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            input = input + (char) (col + 97);
            input = input + (char) (row + 49) + " ";

        }while(!game.takeTurn(input));

        game.drawBoard();
        game.setWhiteTurn(!game.isWhiteTurn());
        swapTurnBox();
        games.add(new Game(game));
        System.out.println(games.size());
    }



    private void endDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Game over");

        // Setting Dialog Message
        if(game.getWhite().isDefeated())
            alertDialog.setMessage("Black wins!");
        else if(game.getBlack().isDefeated())
            alertDialog.setMessage("White wins!");
        else
            alertDialog.setMessage("Draw");


        // Setting save Button
        alertDialog.setButton("Save Game", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                //save game

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //Setting discard button
        alertDialog.setButton2("Discard Game", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                //discard game

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void illegalDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Illegal move");

        // Setting Dialog Message
        alertDialog.setMessage("Illegal move made. Try again");


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
