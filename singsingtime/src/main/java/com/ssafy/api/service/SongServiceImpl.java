package com.ssafy.api.service;

import com.ssafy.api.request.MySongAddPostReq;
import com.ssafy.api.request.SongRegisterPostReq;
import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserSong;
import com.ssafy.db.repository.SongRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("songService")
public class SongServiceImpl implements SongService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSongRepository userSongRepository;
    @Autowired
    SongRepository songRepository;

    @Override
    public void addMySong(String userId, MySongAddPostReq addInfo) {

        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Song> song = songRepository.findById(Long.parseLong(addInfo.getSongId()));

        UserSong userSong = new UserSong();
        userSong.setUser(user.orElseThrow(()->new NoSuchElementException()));
        userSong.setSong(song.orElseThrow(()->new NoSuchElementException()));

        userSongRepository.save(userSong);
    }

    @Override
    public void registerSong(SongRegisterPostReq registerInfo) {
        Song song = new Song();
        song.setTitle(registerInfo.getTitle());
        song.setSinger(registerInfo.getSinger());
        song.setPart1(registerInfo.getPart1());
        song.setPart2(registerInfo.getPart2());
        song.setPart3(registerInfo.getPart3());
        song.setPart4(registerInfo.getPart4());
        songRepository.save(song);
    }

    @Override
    public List<Song> getMySongList(Long userId) {
        List<UserSong> userSongList= userSongRepository.findAllByUserId(userId);
        List<Song> songList = new ArrayList<>();
        for (UserSong userSong: userSongList) {
            songList.add(userSong.getSong());
        }
        return songList;
    }

    @Override
    public List<Song> getAllSongList() {
//        List<UserSong> userSongList= userSongRepository.findAll();
//        List<Song> songList=new ArrayList<>();
//        for (UserSong userSong: userSongList) {
//            songList.add(userSong.getSong());
//        }
        List<Song> songList = songRepository.findAll();
        return songList;
    }

    @Override
    public void deleteMySong(Long userSongId) {
        userSongRepository.deleteById(userSongId);
    }


//    @Override
//    public List<Song> getChampionSongList(String championUserId) {
//        List<UserSong> userSongList = userSongRepository.findAllByUserId(championUserId);
//        List<Song> songList=new ArrayList<>();
//        for (UserSong userSong: userSongList) {
//            songList.add(userSong.getSong());
//        }
//        return songList;
//    }
}
