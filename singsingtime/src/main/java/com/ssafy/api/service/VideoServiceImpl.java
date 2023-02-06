package com.ssafy.api.service;

import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{
    @Autowired
    private final VideoRepository videoRepository;
    @Override
    public List<Video> getAllVideo() {
        return videoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Video> searchVideo(String keyword) {
        return videoRepository.findByTitleContains(keyword);
    }

    @Autowired
    private S3Uploader s3Uploader;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override @Transactional
    public void uploadVideo(MultipartFile file, VideoRegisterPostReq videoRegisterPostReq, String userId) throws IOException {
//        System.out.println("Diary service saveDiary");
        Video video = new Video();
        video.setUserId(userId);
        video.setTitle(videoRegisterPostReq.getTitle());
        video.setDescription(videoRegisterPostReq.getContext());
        if(!file.isEmpty()) {
            String storedFileName = s3Uploader.upload(file,"images");
            video.setUrl(storedFileName);
        }
        videoRepository.save(video);
    }
}