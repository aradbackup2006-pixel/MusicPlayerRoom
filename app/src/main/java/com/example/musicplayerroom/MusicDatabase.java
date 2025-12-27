package com.example.musicplayerroom;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MusicEntity.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {

    private static MusicDatabase instance;

    public abstract MusicDao musicDao();

    public static synchronized MusicDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MusicDatabase.class,
                    "music_db"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }
}
