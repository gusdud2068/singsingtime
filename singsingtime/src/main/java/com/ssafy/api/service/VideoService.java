package com.ssafy.api.service;

import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.api.request.VideoUpdatePatchReq;
import com.ssafy.db.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface VideoService {
    void uploadVideo(MultipartFile file, VideoRegisterPostReq videoReq, String userId) throws IOException;
    List<Video> getAllVideo();

    List<Video> searchVideo(String keyword);

    void updateVideo(MultipartFile file, VideoUpdatePatchReq videoReq, String userId) throws IOException;

    Video getDetailVideo(Long videoId);

    void deleteVideo(Long videoId);

    void addLikesCnt(Long videoId);
}
