package com.example.musicplayerroom;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUrl;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            if (!isInternetConnected()) {
                Toast.makeText(this, "❌ اینترنت قطع است", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = etUrl.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(this, "URL را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }

            MusicDatabase db = MusicDatabase.getInstance(this);
            db.musicDao().insertMusic(new MusicEntity(url));

            startActivity(new Intent(this, PlayerActivity.class));
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
