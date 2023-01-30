package com.ssafy.api.service;

import com.ssafy.db.entity.Diary;
import com.ssafy.db.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class DiaryServiceImpl implements DiaryService{

    // 의존성 주입
    private final DiaryRepository diaryRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override @Transactional
    public Long keepDiary(MultipartFile image, Diary diary) throws IOException {
        System.out.println("Diary service saveDiary");
        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            diary.setImageUrl(storedFileName);
        }
        Diary savedDiary = diaryRepository.save(diary);
        return savedDiary.getDiary_id();
    }

    @Override @Transactional
    public Long keepVideo(MultipartFile video, Diary diary) throws IOException {
        System.out.println("Diary service saveDiary");
        if(!video.isEmpty()) {
            String storedFileName = s3Uploader.upload(video,"images");
            diary.setImageUrl(storedFileName);
        }
        Diary savedDiary = diaryRepository.save(diary);
        return savedDiary.getDiary_id();
    }
}