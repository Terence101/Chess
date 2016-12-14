package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import app.Game;
import pieces.Piece;
import app.Board;

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
    private static ArrayList<Board> moves;

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

        moves = new ArrayList<>();
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
                            openDialog("Illegal Move");
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
                            openDialog("Illegal Move");
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
            resign();
        }

        if(buttonPressed == draw){
            message.setText("draw");
            draw();
        }

    }

    private void openDialog(String s){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage(s);


        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();
                System.out.println("Error acknowledged");
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    private void resign () {
        input = "resign";

        if (game.takeTurn(input)) {

            if (game.isWhiteTurn()) {
                resultDialog("White resigns");
            } else {
                resultDialog("Black resigns");
            }
        }
    }

    private void draw () {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Draw Offered");

        // Setting Dialog Message
        if(game.isWhiteTurn())
            alertDialog.setMessage("White offers draw. \nDo you accept draw?");
        else
            alertDialog.setMessage("Black offers draw. \nDo you accept draw?");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();
                resultDialog("Game ended in a draw");
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                alertDialog.dismiss();
                openDialog("Draw refused");
            }
        });

        alertDialog.show();
    }

    private void resultDialog (String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Game Result");

        // Setting Dialog Message
        alertDialog.setMessage(s);


        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();

                System.out.println("Game ended");
                save();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    private void save () {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Record Game?");

        // Setting Dialog Message
        alertDialog.setMessage("Would you like to save this game?");

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                alertDialog.dismiss();

                save_game();
            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                alertDialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();;
                    }
                }, 3000);
            }
        });

        alertDialog.show();

    }

    private void save_game () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Title");

        final String[] text = new String[1];

        final EditText title = new EditText(this);
        title.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(title);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                text[0] = title.getText().toString();
                System.out.println(text[0]);

                if (text[0] == null || text[0].equals("")) {
                    openDialog("Invalid Name");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            save_game();;
                        }
                    }, 5000);

                } else {
                    writeTofile(text[0]);
                }

                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();;
                    }
                }, 3000);

            }
        });

        builder.show();
    }

    private void writeTofile (String file_name) {
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = openFileOutput(file_name, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(moves);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static ImageButton getButton(int row, int col){
        return buttons[row][col];
    }

    public static ArrayList<Board> getMoves () {
        return moves;
    }

}
