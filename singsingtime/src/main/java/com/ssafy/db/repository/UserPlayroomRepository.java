package com.ssafy.db.repository;

import com.ssafy.db.entity.Reply;
import com.ssafy.db.entity.UserPlayroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPlayroomRepository  extends JpaRepository<UserPlayroom, Long> {
    List<UserPlayroom> findAllBySessionId(String sessionId);
    void deleteBySessionIdAndUserId(String sessionId,String userId);

}
