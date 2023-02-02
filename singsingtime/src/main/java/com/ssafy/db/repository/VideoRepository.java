package com.ssafy.db.repository;

import com.ssafy.api.request.VideoRegisterPostReq;
import com.ssafy.db.entity.Diary;
import com.ssafy.db.entity.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> getVideoByUserId(String userId);

    @Query(value = "SELECT * FROM Video  WHERE created_at BETWEEN DATE_ADD(NOW(), INTERVAL -1 DAY) and now();", nativeQuery = true)
    List<Video> getDailyVideo();

    @Query(value = "SELECT * FROM Video WHERE created_at BETWEEN DATE_ADD(NOW(), INTERVAL -1 WEEK) AND NOW();", nativeQuery = true)
    List<Video> getWeeklyVideo();
    
}
