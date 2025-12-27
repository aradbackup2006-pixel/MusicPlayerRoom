package com.example.musicplayerroom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music_table")
public class MusicEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String url;

    public MusicEntity(String url) {
        this.url = url;
    }
}
