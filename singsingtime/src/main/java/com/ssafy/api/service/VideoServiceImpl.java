package com.ssafy.api.service;

import com.ssafy.api.request.ReplyCreatePostReq;
import com.ssafy.api.request.ReplyUpdatePutReq;
import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.api.request.VideoUpdatePatchReq;
import com.ssafy.db.entity.Reply;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Video;
import com.ssafy.db.repository.ReplyRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class VideoServiceImpl implements VideoService{
    @Autowired
    private final VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Override
    public List<Video> getAllVideo() {
        return videoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Video> searchVideo(String keyword) {
        return videoRepository.findByTitleContains(keyword);
    }

    @Override
    public void updateVideo(MultipartFile file, VideoUpdatePatchReq videoReq, String userId) throws IOException {
        Video video = videoRepository.getVideoById(videoReq.getVideoId());
        Optional<User> user = userRepository.findByUserId(userId);
        video.setUser(user.orElseThrow(()-> new NoSuchElementException()));
        video.setTitle(videoReq.getTitle());
        video.setDescription(videoReq.getContext());
        if(!file.isEmpty()) {
            String storedFileName = s3Uploader.upload(file,"images");
            video.setUrl(storedFileName);
        }
        videoRepository.save(video);
    }

    @Transactional
    @Override
    public Video getDetailVideo(Long videoId) {
        Video video = videoRepository.getVideoById(videoId);
        video.setViewCnt(video.getViewCnt() + 1);
        return video;
    }

    @Transactional
    @Override
    public void deleteVideo(Long videoId, String userId) {
        Video video = videoRepository.findById(videoId).orElseThrow(()-> new NoSuchElementException());
        if(video.getUser().getUserId().equals(userId)){
            videoRepository.deleteById(videoId);
        }
    }

    @Transactional
    @Override
    public void addLikesCnt(Long videoId) {
        Video video = videoRepository.getVideoById(videoId);
        video.setLikeCnt(video.getLikeCnt() + 1);
    }

    @Override
    public void createReply(String userId, ReplyCreatePostReq replyReq) {
        Video video = videoRepository.findById(replyReq.getVideoId()).orElseThrow(() -> new NoSuchElementException());
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException());
        Reply reply = Reply.builder()
                .user(user)
                .video(video)
                .context(replyReq.getContext())
                .build();
        replyRepository.save(reply);
    }
    @Transactional
    @Override
    public void updateReply(String userId, ReplyUpdatePutReq replyReq) {
        Reply reply = replyRepository.findById(replyReq.getReplyId()).orElseThrow(()-> new NoSuchElementException());
        if(reply.getUser().getUserId().equals(userId)){
            reply.setContext(replyReq.getContext());
        }
    }
    @Transactional
    @Override
    public void deleteReply(String userId, Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()-> new NoSuchElementException());
        if(reply.getUser().getUserId().equals(userId)){
            replyRepository.deleteById(replyId);
        }
    }

    @Transactional
    @Override
    public void addReplyLikesCnt(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()-> new NoSuchElementException());
        reply.setLikeCnt(reply.getLikeCnt() + 1);
    }

    @Override
    public List<Video> getMyVideo(Long id) {
        List<Video> myVideoList = videoRepository.findAllById(id);
        return myVideoList;
    }

    @Override
    public List<Video> getDailyVideo() {
        List<Video> dailyVideoList = videoRepository.getDailyVideo();
        return dailyVideoList;
    }

    @Override
    public List<Video> getWeeklyVideo() {
        List<Video> weeklyVideoList = videoRepository.getWeeklyVideo();
        return weeklyVideoList;
    }

    @Autowired
    private S3Uploader s3Uploader;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override @Transactional
    public void uploadVideo(MultipartFile file, VideoRegisterPostReq videoReq, String userId) throws IOException {
        Video video = new Video();
        Optional<User> user = userRepository.findByUserId(userId);
        video.setUser(user.orElseThrow(()-> new NoSuchElementException()));
        video.setTitle(videoReq.getTitle());
        video.setDescription(videoReq.getContext());
        if(!file.isEmpty()) {
            String storedFileName = s3Uploader.upload(file,"images");
            video.setUrl(storedFileName);
        }
        videoRepository.save(video);
    }
}