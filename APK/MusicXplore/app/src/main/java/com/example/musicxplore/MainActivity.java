package com.example.musicxplore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView text = findViewById(R.id.text);
        text.setText("Select Music Folder");

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_select) {
            final TextView text = findViewById(R.id.text);
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            mBuilder.setTitle("Choose Music Folder");


            File dir = Environment.getExternalStorageDirectory();
            String rootPath = dir.getAbsolutePath();
            ArrayList<String> follist = getFolders(rootPath);
            final CharSequence[] fol = follist.toArray(new CharSequence[follist.size()]);

            mBuilder.setSingleChoiceItems(fol, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    text.setText(fol[i]);
                    dialogInterface.dismiss();
                }
            });
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getFolders(String rootPath) {
        ArrayList<String> folders = new ArrayList<String>();
        try {
            File f = new File(rootPath);
            File[] files = f.listFiles();
            for (File inFile : files) {
                if (inFile.isDirectory()) {
                    folders.add(inFile.getAbsolutePath());
                }
            }
        }
        catch (Exception e) {
            return null;
        }
        return folders;
    }

}
