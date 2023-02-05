package com.ssafy.api.service;

import com.ssafy.db.entity.Song;

import java.util.List;

public interface SongService {
    void addMySong(String title, String singer, String UserId);

    List<Song> getAllSongList();
    List<Song> getChampionSongList(String championUserId);
}
