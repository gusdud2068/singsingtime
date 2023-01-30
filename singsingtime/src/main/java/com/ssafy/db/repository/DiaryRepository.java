package com.ssafy.db.repository;

import com.ssafy.db.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import Entity.Diary;

import java.util.List;

/*
 * JpaRepository 상속 -> 자동으로 빈 등록 (@Repository 안 달아도 됨)
 * */
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {   // 인터페이스 다중 상속
//    @Override
    List<Diary> findByMonth(String month);
}