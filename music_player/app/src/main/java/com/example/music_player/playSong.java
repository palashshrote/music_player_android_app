package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class playSong extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp1.stop();
        mp1.release();
        updateSeek.interrupt();
    }

    private TextView textView;
    private SeekBar sk;
    private ImageButton btn_play;
    private ImageButton btn_pre;
    private ImageButton btn_next;
    private ArrayList<File> songs;
    private MediaPlayer mp1;
    private String text_content;
    private int pos, total_songs;
    
    Thread updateSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        textView = findViewById(R.id.textView2);
        sk = findViewById(R.id.seekBar);
        btn_play = findViewById(R.id.play);
        btn_pre = findViewById(R.id.pre);
        btn_next = findViewById(R.id.next);
        Button btn_ran = findViewById(R.id.button);
        btn_ran.setBackgroundColor(Color.GRAY);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("songList");
        text_content = intent.getStringExtra("currentSong");
        pos = intent.getIntExtra("position", 0);
        textView.setText(text_content);
        textView.setSelected(true);
        total_songs = songs.size();

        Uri uri = Uri.parse(songs.get(pos).toString());
        mp1 = MediaPlayer.create(this, uri);
        mp1.start();
        btn_play.setImageResource(R.drawable.pause);
        sk.setMax(mp1.getDuration());


        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp1.seekTo(sk.getProgress());
            }
        });

        updateSeek = new Thread()
        {
            @Override
            public void run() {
                int curr_pos = 0;
                try {
                    while(curr_pos < mp1.getDuration())
                    {
                        curr_pos = mp1.getCurrentPosition();
                        sk.setProgress(curr_pos);
                        sleep(800);
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        updateSeek.start();

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp1.isPlaying())
                {
                    btn_play.setImageResource(R.drawable.play);
                    mp1.pause();
                }
                else
                {
                    btn_play.setImageResource(R.drawable.pause);
                    mp1.start();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp1.stop();
                mp1.release();
                if(pos != total_songs-1)
                {
                    pos = pos+1;
                }
                else
                    pos = 0;

                Uri uri = Uri.parse(songs.get(pos).toString());
                mp1 = MediaPlayer.create(getApplicationContext(), uri);
                mp1.start();
                btn_play.setImageResource(R.drawable.pause);
                sk.setMax(mp1.getDuration());
                text_content = songs.get(pos).getName().toString();
                textView.setText(text_content);
            }
        });

        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp1.stop();
                mp1.release();

                if(pos != 0)
                {
                    pos = pos-1;
                }
                else
                {
                    pos = songs.size()-1;
                }
                Uri uri = Uri.parse(songs.get(pos).toString());
                mp1 = MediaPlayer.create(getApplicationContext(), uri);
                mp1.start();
                btn_play.setImageResource(R.drawable.pause);
                sk.setMax(mp1.getDuration());
                text_content = songs.get(pos).getName().toString();
                textView.setText(text_content);
            }
        });

        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp1.stop();
                mp1.release();
                if(pos != total_songs-1)
                {
                    pos = pos+1;
                }
                else
                    pos = 0;

                Uri uri = Uri.parse(songs.get(pos).toString());
                mp1 = MediaPlayer.create(getApplicationContext(), uri);
                mp1.start();
                btn_play.setImageResource(R.drawable.pause);
                sk.setMax(mp1.getDuration());
                text_content = songs.get(pos).getName().toString();
                textView.setText(text_content);
            }
        });
    }
}