package com.ssafy.db.repository;

import com.ssafy.db.entity.UserSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserSongRepository extends JpaRepository<UserSong,Long> {
    List<UserSong> findAllByUserId(Long userId);
//    List<UserSong> findAll();
}
