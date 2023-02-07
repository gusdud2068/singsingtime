package com.ssafy.api.service;

import com.ssafy.api.request.PlayroomReq;
import com.ssafy.db.entity.Playroom;
import com.ssafy.db.entity.Song;

import java.util.List;

public interface PlayroomService {
    public List<Playroom> getPlayroom();
    public List<Playroom> getPlayroomByTitle(String keyword);

    List<Song> getChampionSongList(int sessionId);

    void createRoom(PlayroomReq playroomReq);

}

