package com.example.musicplayerroom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MusicDao {

    @Insert
    void insertMusic(MusicEntity music);

    @Query("SELECT * FROM music_table ORDER BY id DESC LIMIT 1")
    MusicEntity getLastMusic();
}
