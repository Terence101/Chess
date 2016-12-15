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
import android.widget.Switch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import app.Game;

public class RecordActivity extends AppCompatActivity {


    private ListView list;
    private Switch sort;
    private static String s;
    private String[] files;
    private boolean sortByName;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);

        initialize_list();
        s = null;

        sort = (Switch) findViewById(R.id.sort);
        sort.setText("Sort by Name");

        sortByName = false;
    }

    private void initialize_list () {
        list = (ListView) findViewById(R.id.listview);
        files = fileList();
        files = Arrays.copyOfRange(files, 1, files.length);

        if (files == null)
            return;


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                s = (String) list.getItemAtPosition(position);
                adapter.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                System.out.println(s);
            }
        });
    }

    public void switchSort(View v){
        sortByName = !sortByName;

        if(sortByName){
            sortByName();
        }else{
            sortByDate();
        }
    }

    private void sortByDate(){

        String o1 = files[0];

        String date1 = o1.substring(o1.lastIndexOf("\t") + 1);
        System.out.println(date1);

        /*
        Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                String date1 = o1.substring(o1.lastIndexOf("/t"));

            }
        }
        */
    }

    private void sortByName(){

        Arrays.sort(files);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

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
