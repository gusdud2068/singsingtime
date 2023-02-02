package com.ssafy.api.service;

import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.db.entity.Diary;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoServiceImpl implements VideoService{

    // 의존성 주입
    private final VideoRepository videoRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

//    @Override @Transactional
//    public Long keepDiary(MultipartFile image, Video diary) throws IOException {
//        System.out.println("Diary service saveDiary");
//        if(!image.isEmpty()) {
//            String storedFileName = s3Uploader.upload(image,"images");
//            diary.setImageUrl(storedFileName);
//        }
//        Diary savedDiary = videoRepository.save(diary);
//        return savedDiary.getDiary_id();
//    }

    @Override @Transactional
    public void uploadVideo(MultipartFile file, VideoRegisterPostReq videoRegisterPostReq) throws IOException {
//        System.out.println("Diary service saveDiary");
        Video video = new Video();
        video.setUserId(videoRegisterPostReq.getId());
        video.setTitle(videoRegisterPostReq.getTitle());
        video.setDescription(videoRegisterPostReq.getContext());
        if(!file.isEmpty()) {
            String storedFileName = s3Uploader.upload(file,"images");
            video.setUrl(storedFileName);
        }
        videoRepository.save(video);
//        return saveVideo.
    }
}