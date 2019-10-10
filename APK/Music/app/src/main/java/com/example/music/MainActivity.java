package com.example.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = findViewById(R.id.just);
        text.setText("");

    }



    MediaPlayer mediaPlayer = new MediaPlayer();

    public void play(View view) throws IOException {

        TextView text = findViewById(R.id.just);
        View play = findViewById(R.id.play);
        View pause = findViewById(R.id.pause);
        View next = findViewById(R.id.next);
        pause.setClickable(true);
        play.setClickable(false);
        next.setClickable(true);
        if(mediaPlayer == null) {
            mediaPlayer.reset();
        }
        if(text.getText() == "") {
            ArrayList<String> actualSong;
            String rootPath, name, song;
            //rootPath = Environment.getExternalStorageDirectory().toString();
            //rootPath = getFilesDir().getAbsolutePath();
            rootPath = "/storage/emulated/0/Playlists/";
            actualSong = getPlayList(rootPath);
            //int num = actualSong.size();
            song = actualSong.get(0);
            Uri myUri = Uri.parse(song);
            name = getName(song);
            text.setText(name);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
            mediaPlayer.prepare();
        }
        mediaPlayer.start();
    }

    public void pause(View view) {
        if(mediaPlayer != null) {
            View play = findViewById(R.id.play);
            View pause = findViewById(R.id.pause);
            View next = findViewById(R.id.next);
            pause.setClickable(false);
            play.setClickable(true);
            next.setClickable(true);
            mediaPlayer.pause();
        }
    }

    public void next(View view) {
        mediaPlayer.stop();
        mediaPlayer.reset();
        View play = findViewById(R.id.play);
        View pause = findViewById(R.id.pause);
        pause.setClickable(true);
        play.setClickable(false);
        TextView text = findViewById(R.id.just);
        String current = (String) text.getText();
        current = current.concat(".mp3");
        String rootPath;
        //rootPath = Environment.getExternalStorageDirectory().toString();
        //rootPath = getFilesDir().getAbsolutePath();
        rootPath = "/storage/emulated/0/Playlists/";
        ArrayList<String> actualSong = getPlayList(rootPath);
        int num = actualSong.size();
        String currentsong = rootPath.concat(current);
        int ind = 0;
        ind = actualSong.indexOf(currentsong);
        ind = ind + 1;
        if(ind == num) {
            ind = 0;
        }
        Uri uri = Uri.parse(actualSong.get(ind));
        text.setText(getName(actualSong.get(ind)));

        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }


    public void stop(View view) {
        //stopPlayer();
        View play = findViewById(R.id.play);
        View pause = findViewById(R.id.pause);
        View next = findViewById(R.id.next);
        next.setClickable(false);
        pause.setClickable(false);
        play.setClickable(true);
        TextView text = findViewById(R.id.just);
        text.setText("");
        mediaPlayer.stop();
        mediaPlayer.reset();
        Toast.makeText(this, "Music Stopped", Toast.LENGTH_SHORT).show();
    }

    private void stopPlayer() {
        TextView text = findViewById(R.id.just);
        View play = findViewById(R.id.play);
        View pause = findViewById(R.id.pause);
        View next = findViewById(R.id.next);
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            text.setText("");
            Toast.makeText(this, "Task Ended", Toast.LENGTH_SHORT).show();
            play.setClickable(true);
            next.setClickable(false);
            pause.setClickable(false);

        }
    }

    public String getName(String detail) {
        String name;
        int li, ei;
        li = detail.lastIndexOf("/");
        ei = detail.length();
        ei = ei - 4;
        name = detail.substring(li + 1, ei);
        return name;
    }
    ArrayList<String> getPlayList(String rootPath) {
        ArrayList<String> fileList = new ArrayList<>();


        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {

                    fileList.add(file.getAbsolutePath());
                    //"file_name", file.getName());
                }
            }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }


    //@Override
    //protected void onStop() {
    //    super.onStop();
     //   stopPlayer();
    //}
}
