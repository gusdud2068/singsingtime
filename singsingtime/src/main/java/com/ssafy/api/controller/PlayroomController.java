package com.ssafy.api.controller;

import com.ssafy.api.request.PlayroomReq;
import com.ssafy.api.service.PlayroomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.db.entity.Playroom;
import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playrooms")
public class PlayroomController {

    @Autowired
    PlayroomService playroomService;
    @Autowired
    UserService userService;
    // 방 목록 조회
    @GetMapping("/")
    public ResponseEntity<List<Playroom>> getRoomInfo() {
        List<Playroom> list = playroomService.getPlayroom();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    // 노래 제목으로 방 목록 조회
    @GetMapping("/{keyword}")
    public ResponseEntity<List<Playroom>> getRoomInfoByKeyword(@PathVariable String keyword) {
        List<Playroom> list = playroomService.getPlayroomByTitle(keyword);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    /**
     * 해당 플레이룸의 챔피언 노래 리스트 목록 조회 API
     * @param sessionId
     * @return List<Song>
     */
    @GetMapping("/playlist/{sessionId}")
    public ResponseEntity<List<Playroom>> getChampionSongList(@PathVariable int sessionId) {

        List<Song> list = playroomService.getChampionSongList(sessionId);
        return new ResponseEntity(list, HttpStatus.OK);
    }

//    @PostMapping("/playlist/")
//    public ResponseEntity<Song> choiceSong(Song song) {
//        playroomService.choiceSong();
//
//        return new ResponseEntity(list, HttpStatus.OK);
//    }

    @PostMapping("/sessions")
    public ResponseEntity<String> createRoom(PlayroomReq playroomReq, Authentication authentication) {
        int sessionId = playroomReq.getSessionId();
        String title = playroomReq.getTitle();
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();
        User user = userService.getUserByUserId(userId);
        long ownerId = user.getId();
        playroomService.createRoom(playroomReq);
        return new ResponseEntity("방 생성 성공!",HttpStatus.OK);
    }


}
