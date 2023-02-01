package com.ssafy.api.service;

import com.ssafy.db.entity.Diary;
import com.ssafy.db.entity.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface VideoService {
//    public Long keepDiary(MultipartFile image, Diary diary) throws IOException;

    public void uploadVideo(MultipartFile file, Video video) throws IOException;


}
