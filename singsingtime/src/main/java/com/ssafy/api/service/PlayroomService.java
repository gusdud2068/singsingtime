package com.ssafy.api.service;

import com.ssafy.api.request.PlayroomCreateReq;
import com.ssafy.api.request.PlayroomStatusReq;
import com.ssafy.api.response.PlayroomStatusRes;
import com.ssafy.db.entity.Playroom;
import com.ssafy.db.entity.Song;

import java.util.List;

public interface PlayroomService {
    public List<Playroom> getPlayroom();
    public List<Playroom> getPlayroomByTitle(String keyword);

    public List<Song> getChampionSongList(int sessionId);

    public void createRoom(PlayroomCreateReq playroomCreateReq);

    void startSong(PlayroomStatusReq playroomStatusReq);

    public PlayroomStatusRes endSong(PlayroomStatusReq playroomStatusReq);
}

