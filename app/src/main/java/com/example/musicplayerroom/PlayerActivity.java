package com.example.musicplayerroom;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    Button btnPlay, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // --- تست: اضافه کردن URL به دیتابیس ---
        MusicDatabase db = MusicDatabase.getInstance(this);
        db.musicDao().insertMusic(new MusicEntity(
                "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3"
        ));
        // --- پایان بخش تست ---

        // پیدا کردن دکمه‌ها
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        // دکمه پخش
        btnPlay.setOnClickListener(v -> {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo == null || !netInfo.isConnected()) {
                Toast.makeText(this, "اینترنت وصل نیست!", Toast.LENGTH_SHORT).show();
                return;
            }

            playMusic();
        });

        // دکمه توقف
        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
    }

    // متد پخش موزیک غیرهمزمان
    private void playMusic() {
        MusicDatabase db = MusicDatabase.getInstance(this);
        MusicEntity lastMusic = db.musicDao().getLastMusic();

        if (lastMusic == null || lastMusic.url == null) {
            Toast.makeText(this, "هیچ موزیکی ذخیره نشده", Toast.LENGTH_SHORT).show();
            return;
        }

        String musicUrl = lastMusic.url;

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(musicUrl);
                mediaPlayer.setOnPreparedListener(mp -> mp.start()); // وقتی آماده شد پخش کن
                mediaPlayer.prepareAsync(); // آماده‌سازی غیرهمزمان

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "خطا در پخش موزیک", Toast.LENGTH_SHORT).show();
            }
        } else {
            mediaPlayer.start();
        }
    }

    // آزادسازی MediaPlayer هنگام بسته شدن Activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
