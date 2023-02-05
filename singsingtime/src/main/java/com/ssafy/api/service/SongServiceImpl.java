package com.ssafy.api.service;

import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserSong;
import com.ssafy.db.repository.SongRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserSongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("songService")
public class SongServiceImpl implements SongService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSongRepository userSongRepository;
    @Autowired
    SongRepository songRepository;

    @Override
    public void addMySong(String title, String singer,String userId) {

        Song song = songRepository.findByTitleAndSinger(title,singer);
        Optional<User> user = userRepository.findByUserId(userId);
        UserSong userSong = new UserSong(user.get(),song);
        userSongRepository.save(userSong);
    }

    @Override
    public List<Song> getAllSongList() {
        List<UserSong> userSongList= userSongRepository.findAll();
        List<Song> songList=new ArrayList<>();
        for (UserSong userSong: userSongList) {
            songList.add(userSong.getSong());
        }
        return songList;
    }

    @Override
    public List<Song> getChampionSongList(String championUserId) {
        List<UserSong> userSongList = userSongRepository.findAllByUserId(championUserId);
        List<Song> songList=new ArrayList<>();
        for (UserSong userSong: userSongList) {
            songList.add(userSong.getSong());
        }
        return songList;
    }
}
