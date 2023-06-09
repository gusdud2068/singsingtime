package com.ssafy.api.service;
import com.ssafy.api.request.PlayroomStatusReq;
import com.ssafy.api.response.PlayroomStatusRes;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.ssafy.db.entity.Playroom;
import com.ssafy.db.repository.PlayroomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayroomServiceImpl implements PlayroomService{

    private final PlayroomRepository playroomRepository;

    private final UserSongRepository userSongRepository;
    private final UserRepository userRepository;

    private final UserPlayroomRepository userPlayroomRepository;

    @Override
    public List<Playroom> getPlayroom() {
        List<Playroom> list = playroomRepository.findAll();
        return list;
    }

    @Override
    public Playroom getPlayroomDetail(String sessionId) {
        Playroom playroom = playroomRepository.findBySessionId(sessionId).orElseThrow(()->new NoSuchElementException());
        return playroom;
    }

    @Override
    public List<Playroom> getPlayroomByTitle(String keyword) {
        List<Playroom> list = playroomRepository.findByTitle(keyword);
        return list;
    }

    @Override
    public List<Song> getChampionSongList(String sessionId) {
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

//    @Override
//    public void createRoom(PlayroomCreateReq playroomCreateReq) {
//        User user = userRepository.findById(playroomCreateReq.getOwnerId()).orElseThrow(()->new NoSuchElementException());
//        System.out.println("playroomCreateReq.getTitle() 57= " + playroomCreateReq.getTitle());
//        System.out.println("playroomCreateReq.getSessionId() = " + playroomCreateReq.getSessionId());
//        Playroom playroom = new Playroom(playroomCreateReq.getSessionId(),playroomCreateReq.getTitle(),user);
//
//        playroomRepository.save(playroom);
//        return ;
//    }

    @Transactional
    @Override
    public void startSong(PlayroomStatusReq playroomStatusReq) {
        String title = playroomStatusReq.getTitle();
        String sessionId = playroomStatusReq.getSessionId();

        Playroom playroom = playroomRepository.findBySessionId(sessionId).orElseThrow(()->new NoSuchElementException());
        playroom.setCurPlay(title);
        playroom.setChampion(playroomStatusReq.getChampionId());
//        List<Song> championSongList = getChampionSongList(sessionId);

        //Playroom 상태 변경
        playroom.setPlayroomStatus(PlayroomStatus.PLAYING);
    }
    @Transactional
    @Override
    public PlayroomStatusRes endSong(PlayroomStatusReq playroomStatusReq) {
        System.out.println("playroomStatusReq.getChampionId() = " + playroomStatusReq.getChampionId());
        System.out.println("playroomStatusReq.getSessionId() = " + playroomStatusReq.getSessionId());
        String sessionId = playroomStatusReq.getSessionId();
        String newChampion = playroomStatusReq.getChampionId();
        Playroom curPlayroom = playroomRepository.findBySessionId(sessionId).get();
        curPlayroom.setCurPlay(null);

        // 챔피언 이름 비교 후 초기화 or 연승 cnt 증가
        String originChamion = curPlayroom.getChampion();
//        if(originChamion==null)
        System.out.println("originChamion = " + originChamion);
        if(newChampion.equals(originChamion)){
            curPlayroom.setWinCnt(curPlayroom.getWinCnt()+1);
        }else{
            curPlayroom.setWinCnt(1);
            curPlayroom.setChampion(newChampion);
        }
        // Playroom 상태 변경
        curPlayroom.setPlayroomStatus(PlayroomStatus.STANDBY);
        // 챔피언 곡 리스트 반영
        List<Song> championSongList = getChampionSongList(sessionId);
        System.out.println(championSongList);
        PlayroomStatusRes playroomStatusRes = new PlayroomStatusRes();
        playroomStatusRes.setChampionSongList(championSongList);
        playroomStatusRes.setWinCnt(curPlayroom.getWinCnt());
        return playroomStatusRes;
    }

    @Override
    public int checkPlayRoom(Map<String, Object> params) {
        String sessionId = (String) params.get("customSessionId");
        System.out.println("sessionId = " + sessionId);
        Optional<Playroom> playroom = playroomRepository.findBySessionId(sessionId);
        return playroom.isPresent() ? 1 : 0;
    }

    @Override
    public void createPlayRoom(Map<String, Object> params, User user) {
        String sessionId = (String) params.get("customSessionId");
        System.out.println("sessionId = " + sessionId);
        Playroom playroom = Playroom.builder()
                .user(user)
                .title(sessionId)
                .sessionId(sessionId)
                .build();
        playroomRepository.save(playroom);
    }

    @Override
    public int checkPlayRoomMemberCnt(String sessionId) {
        Optional<Playroom> playroom = playroomRepository.findBySessionId(sessionId);
        int memberCnt = playroom.orElseThrow(()->new NoSuchElementException()).getUserCnt();
        return memberCnt;
    }

    @Transactional
    @Override
    public void addPlayRoomMemberCnt(String sessionId) {
        Playroom playroom = playroomRepository.findBySessionId(sessionId).orElseThrow(()->new NoSuchElementException());
        playroom.setUserCnt(playroom.getUserCnt()+1);
    }

    @Transactional
    @Override
    public void minusPlayRoomMemberCnt(String sessionId) {
        Playroom playroom = playroomRepository.findBySessionId(sessionId).orElseThrow(()->new NoSuchElementException());
        playroom.setUserCnt(playroom.getUserCnt()-1);
    }

    @Transactional
    @Override
    public void deletePlayRoom(String sessionId) {
        Playroom playroom = playroomRepository.findBySessionId(sessionId).orElseThrow(()->new NoSuchElementException());
        playroomRepository.deleteById(playroom.getId());
    }

    @Transactional
    @Override
    public void updateChallenger(PlayroomStatusReq playroomStatusReq) {
        Playroom playroom = playroomRepository.findBySessionId(playroomStatusReq.getSessionId()).orElseThrow(()->new NoSuchElementException());
        playroom.setChallenger(playroomStatusReq.getChallengerId());
    }

    @Override
    public void postUserPlayroom(PlayroomStatusReq playroomStatusReq) {
        String sessionId = playroomStatusReq.getSessionId();
        String challenger = playroomStatusReq.getChallengerId();
        UserPlayroom userPlayroom = new UserPlayroom();
        userPlayroom.setSessionId(sessionId);
        userPlayroom.setUserId(challenger);
        userPlayroomRepository.save(userPlayroom);
        return;
    }
    @Override
    public List<String> getUserPlayroom(String sessionId) {
        List<UserPlayroom> playroomList = userPlayroomRepository.findAllBySessionId(sessionId);
        List<String> list = new ArrayList<>();
        for(int i=0;i<playroomList.size();i++){
        list.add(playroomList.get(i).getUserId());
        }
        return list;
    }
    @Override
    public void deleteUserPlayroom(PlayroomStatusReq playroomStatusReq) {
        String sessionId = playroomStatusReq.getSessionId();
        String challenger = playroomStatusReq.getChallengerId();
        userPlayroomRepository.deleteBySessionIdAndUserId(sessionId,challenger);
        return;
    }
}
