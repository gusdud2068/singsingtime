package com.ssafy.api.service;

import com.ssafy.db.entity.Diary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface DiaryService {
    public Long keepDiary(MultipartFile image, Diary diary) throws IOException;

    public Long keepVideo(MultipartFile video, Diary diary) throws IOException;
}
