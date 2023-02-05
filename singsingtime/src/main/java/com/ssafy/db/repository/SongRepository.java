package com.ssafy.db.repository;

import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.UserSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song,Long> {
    @Override
    Optional<Song> findById(Long id);
    Song findByTitleAndSinger(String title,String singer);

}
