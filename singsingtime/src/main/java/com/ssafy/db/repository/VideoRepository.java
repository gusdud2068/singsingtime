package com.ssafy.db.repository;

import com.ssafy.db.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllById(Long id);
    Video getVideoById(Long videoId);


    @Query(value = "SELECT * FROM video WHERE created_at BETWEEN now() and DATE_ADD(NOW(), INTERVAL 1 DAY) ORDER BY view_cnt desc LIMIT 5;", nativeQuery = true)
    List<Video> getDailyVideo();

    @Query(value = "SELECT * FROM video WHERE created_at BETWEEN now() and DATE_ADD(NOW(), INTERVAL 1 WEEK) ORDER BY view_cnt desc LIMIT 5;", nativeQuery = true)
    List<Video> getWeeklyVideo();

    List<Video> findByTitleContains(String keyword);

    List<Video> findAllByUserId(Long userId);
}
