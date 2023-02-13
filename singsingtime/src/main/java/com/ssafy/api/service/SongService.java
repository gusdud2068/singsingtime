package com.ssafy.api.service;

import com.ssafy.api.request.MySongAddPostReq;
import com.ssafy.api.request.SongRegisterPostReq;
import com.ssafy.db.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SongService {
    void addMySong(String userId, MySongAddPostReq addInfo);
    void registerSong(SongRegisterPostReq registerInfo);
    List<Song> getMySongList(Long userId);

    List<Song> getAllSongList();

    void deleteMySong(Long userSongId);

//    List<Song> getChampionSongList(String championUserId);

}
