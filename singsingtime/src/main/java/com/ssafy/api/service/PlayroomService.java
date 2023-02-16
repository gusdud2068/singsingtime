package com.ssafy.api.service;

import com.ssafy.api.request.PlayroomStatusReq;
import com.ssafy.api.response.PlayroomStatusRes;
import com.ssafy.db.entity.Playroom;
import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.User;

import java.util.List;
import java.util.Map;

public interface PlayroomService {
    public List<Playroom> getPlayroom();
    Playroom getPlayroomDetail(String sessionId);
    public List<Playroom> getPlayroomByTitle(String keyword);

    public List<Song> getChampionSongList(String sessionId);

//    public void createRoom(PlayroomCreateReq playroomCreateReq);

    void startSong(PlayroomStatusReq playroomStatusReq);

    public PlayroomStatusRes endSong(PlayroomStatusReq playroomStatusReq);

    int checkPlayRoom(Map<String, Object> params);

    void createPlayRoom(Map<String, Object> params, User user);

    int checkPlayRoomMemberCnt(String sessionId);

    void addPlayRoomMemberCnt(String sessionId);

    void minusPlayRoomMemberCnt(String sessionId);

    void deletePlayRoom(String sessionId);

    void updateChallenger(PlayroomStatusReq playroomStatusReq);
    void postUserPlayroom(PlayroomStatusReq playroomStatusReq);
    List<String> getUserPlayroom(String sessionId);
    void deleteUserPlayroom(PlayroomStatusReq playroomStatusReq);
}
