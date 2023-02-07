package com.ssafy.api.service;

import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.db.entity.Diary;
import com.ssafy.db.entity.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface VideoService {
    public void uploadVideo(MultipartFile file, VideoRegisterPostReq videoRegisterPostReq, String userId) throws IOException;
    List<Video> getAllVideo();

    List<Video> searchVideo(String keyword);
}
