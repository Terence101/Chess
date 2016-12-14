package com.example.jar424.chess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

import app.Game;

public class RecordActivity extends AppCompatActivity {


    private ListView list;
    private static String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);

        initialize_list();
        s = null;
    }

    private void initialize_list () {
        list = (ListView) findViewById(R.id.listview);
        String[] files = fileList();

        if (files == null)
            return;


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arrays.copyOfRange(files, 1, files.length));
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                s = (String) list.getItemAtPosition(position);
                adapter.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                System.out.println(s);
            }
        });
    }

    private void openDialog(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert Dialog");

        // Setting Dialog Message
        alertDialog.setMessage(s);


        // Setting OK Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which)
            {
                System.out.println("Error acknowledged");
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void playback (View v) {

        if (s == null) {
            openDialog("No Game selected");
            return;
        }

        Intent intent = new Intent(this, PlaybackActivity.class);
        startActivity(intent);
    }

    public static String getSelected () {
        return s;
    }

}
