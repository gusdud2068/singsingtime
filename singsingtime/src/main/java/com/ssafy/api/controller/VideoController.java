package com.ssafy.api.controller;

//import com.ssafy.api.service.DiaryService;
import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.api.request.VideoUpdatePatchReq;
import com.ssafy.api.service.VideoService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired

    VideoRepository videoRepository;

    @GetMapping()
    public ResponseEntity<List<Video>> getAllVideo(){
        List<Video> videoList = videoService.getAllVideo();
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

    @GetMapping("/sort/daily")
    public ResponseEntity<List<Video>> getDailyVideo(){
        List<Video> list = videoRepository.getDailyVideo();
        for(Video video : list){
            System.out.println("daily video = " + video);
        }
        return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
    }

    @GetMapping("/sort/weekly")
    public ResponseEntity<List<Video>> getWeeklyVideo(){
        List<Video> list = videoRepository.getWeeklyVideo();
        for(Video video : list){
            System.out.println("weekly video = " + video);
        }
        return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
    }


    @GetMapping("/sort/myvideo/{userId}")
    public ResponseEntity<List<Video>> getMyVideo(@PathVariable String userId){
        List<Video> list = videoRepository.getVideoByUserId(userId);
        for(Video video : list){
            System.out.println("my video = " + video);
        }
        return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
    }

}
