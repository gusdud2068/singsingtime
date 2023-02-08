package com.ssafy.api.service;
import com.ssafy.api.request.PlayroomReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Playroom;
import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.UserSong;
import com.ssafy.db.repository.PlayroomRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayroomServiceImpl implements PlayroomService{

    private final PlayroomRepository playroomRepository;

    private final UserSongRepository userSongRepository;
    private final UserRepository userRepository;
    @Override
    public List<Playroom> getPlayroom() {
        List<Playroom> list = playroomRepository.findAll();
        return list;
    }

    @Override
    public List<Playroom> getPlayroomByTitle(String keyword) {
        List<Playroom> list = playroomRepository.findByTitle(keyword);
        return list;
    }

    @Override
    public List<Song> getChampionSongList(int sessionId) {
        Playroom playroom = playroomRepository.findBySessionId(sessionId).get();

        String championId = playroom.getChampion();
        User champion = userRepository.findByUserId(championId).get();
        Long id = champion.getId();
        List< UserSong > userSongList= userSongRepository.findAllByUserId(id);
        List<Song> championSongList = new ArrayList<>();
        for (UserSong userSong: userSongList) {
            championSongList.add(userSong.getSong());
        }
        return championSongList;
    }

    @Override
    public void createRoom(PlayroomReq playroomReq) {
        User user = userRepository.findById(playroomReq.getOwnerId()).get();
        Playroom playroom = new Playroom(playroomReq.getSessionId(),playroomReq.getTitle(),user);
        playroomRepository.save(playroom);
        return ;
    }
}

