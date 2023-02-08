package com.ssafy.api.controller;

//import com.ssafy.api.service.DiaryService;
import com.ssafy.api.request.*;
import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.api.request.VideoUpdatePatchReq;
import com.ssafy.api.service.UserService;
import com.ssafy.api.service.VideoService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<Video>> getAllVideo(){
        List<Video> videoList = videoService.getAllVideo();
        return ResponseEntity.status(200).body(videoList);
    }
    @GetMapping("/myvideo")
    public ResponseEntity<List<Video>> getMyVideo(@ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();
        User user = userService.getUserByUserId(userId);
        Long id = user.getId();
        List<Video> videoList = videoService.getMyVideo(id);
        return ResponseEntity.status(200).body(videoList);
    }

    @GetMapping("/daily-video")
    public ResponseEntity<List<Video>> getDailyVideo(){
        List<Video> videoList = videoService.getDailyVideo();
        return ResponseEntity.status(200).body(videoList);
    }
    @GetMapping("/weekly-video")
    public ResponseEntity<List<Video>> getWeeklyVideo(){
        List<Video> videoList = videoService.getWeeklyVideo();
        return ResponseEntity.status(200).body(videoList);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<? extends BaseResponseBody> uploadVideo(@RequestParam(value="video") MultipartFile file, VideoRegisterPostReq videoReq, Authentication auth) throws IOException {
        SsafyUserDetails userDetails = (SsafyUserDetails)auth.getDetails();
        String userId = userDetails.getUsername();
        videoService.uploadVideo(file, videoReq, userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<? extends BaseResponseBody> updateVideo(@RequestParam(value="video") MultipartFile file, VideoUpdatePatchReq videoReq, Authentication auth) throws IOException {
        SsafyUserDetails userDetails = (SsafyUserDetails)auth.getDetails();
        String userId = userDetails.getUsername();
        videoService.updateVideo(file, videoReq, userId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getDetailVideo(@PathVariable Long videoId) {
        Video video = videoService.getDetailVideo(videoId);
        return ResponseEntity.status(200).body(video);
    }

    @DeleteMapping ("/{videoId}")
    public ResponseEntity<? extends BaseResponseBody> deleteVideo(@PathVariable Long videoId){
        videoService.deleteVideo(videoId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping ("/likes/{videoId}")
    public ResponseEntity<? extends BaseResponseBody> addLikesCnt(@PathVariable Long videoId){
        videoService.addLikesCnt(videoId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Video>> searchVideo(@PathVariable String keyword) {
        List<Video> videoList = videoService.searchVideo(keyword);
        return ResponseEntity.status(200).body(videoList);
    }


    @PostMapping("/replys")
    public ResponseEntity<? extends BaseResponseBody> createReply(Authentication auth, @RequestBody ReplyCreatePostReq replyReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails)auth.getDetails();
        String userId = userDetails.getUsername();
        videoService.createReply(userId, replyReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/replys")
    public ResponseEntity<? extends BaseResponseBody> updateReply(Authentication auth, @RequestBody ReplyUpdatePutReq replyReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails)auth.getDetails();
        String userId = userDetails.getUsername();
        videoService.updateReply(userId, replyReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/replys/{replyId}")
    public ResponseEntity<? extends BaseResponseBody> deleteReply(Authentication auth, @PathVariable Long replyId) {
        SsafyUserDetails userDetails = (SsafyUserDetails)auth.getDetails();
        String userId = userDetails.getUsername();
        videoService.deleteReply(userId, replyId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/replys/likes/{replyId}")
    public ResponseEntity<? extends BaseResponseBody> addReplyLikesCnt(@PathVariable Long replyId) {
        videoService.addReplyLikesCnt(replyId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


}
