package com.ssafy.api.controller;

//import com.ssafy.api.service.DiaryService;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.api.service.VideoService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Diary;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

//    @ResponseBody   // Long 타입을 리턴하고 싶은 경우 붙여야 함 (Long - 객체)
//    @PostMapping(value="/diary/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Long saveImage(HttpServletRequest request, @RequestParam(value="image") MultipartFile image, Diary diary) throws IOException {
//        System.out.println("DiaryController.saveDiary");
//        System.out.println(image);
//        System.out.println(diary);
//        System.out.println("------------------------------------------------------");
//        Long diaryId = diaryService.keepDiary(image, diary);
//        return diaryId;
//    }

//    @ResponseBody   // Long 타입을 리턴하고 싶은 경우 붙여야 함 (Long - 객체)
    @PostMapping(value="/diary/video",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<? extends BaseResponseBody> saveVideo(@RequestParam(value="video") MultipartFile file, VideoRegisterPostReq videoRegisterPostReq) throws IOException {
//		System.out.println(video);
        videoService.uploadVideo(file, videoRegisterPostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    @Autowired
    VideoRepository videoRepository;

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
