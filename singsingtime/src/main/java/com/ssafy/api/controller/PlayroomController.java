package com.ssafy.api.controller;

import com.ssafy.api.request.PlayroomCreateReq;
import com.ssafy.api.request.PlayroomStatusReq;
import com.ssafy.api.response.PlayroomStatusRes;
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
import springfox.documentation.annotations.ApiIgnore;

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

    @PostMapping("/createroom")
    public ResponseEntity<String> createRoom(@RequestBody PlayroomCreateReq playroomCreateReq, @ApiIgnore Authentication authentication) {
        int sessionId = playroomCreateReq.getSessionId();
        System.out.println("sessionId = " + sessionId);
        String title = playroomCreateReq.getTitle();
        System.out.println("title = " + title);
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();
        User user = userService.getUserByUserId(userId);
        long ownerId = user.getId();
        playroomCreateReq.setOwnerId(ownerId);
        playroomService.createRoom(playroomCreateReq);
        return new ResponseEntity("방 생성 성공!",HttpStatus.OK);
    }
    @PutMapping("/start-song")
    public ResponseEntity<String> startSong(@RequestBody PlayroomStatusReq playroomStatusReq){

        playroomService.startSong(playroomStatusReq);

//        PlayroomStatusRes playroomStatusRes= new PlayroomStatusRes();
        return new ResponseEntity<String>("대결 시작!! ",HttpStatus.OK);
    }

    @PutMapping("/end-song")
    public ResponseEntity<PlayroomStatusRes> endSong(@RequestBody PlayroomStatusReq playroomStatusReq){

        PlayroomStatusRes playroomStatusRes =playroomService.endSong(playroomStatusReq);
        return new ResponseEntity<>(playroomStatusRes,HttpStatus.OK);
    }
}
