package com.ssafy.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diary_id;  // 일기 pk

    @Column(length = 1000)
    private String content; // 일기 내용

    private LocalDate created_at;   // 생성 시간
    @PrePersist // DB에 해당 테이블의 insert 연산을 실행할 때 같이 실행해라
    public void created_at(){
        this.created_at = LocalDate.now();
        setMonth(created_at);
    }

    private String month;   // 연월
    public void setMonth(LocalDate created_at) {
        String year = Integer.toString(created_at.getYear());
        String month = Integer.toString(created_at.getMonthValue());
        this.month = year+month;
    }

    @Column
    private String imageUrl;

    @Column
    private String videoUrl;

    @ManyToOne  // 다대일 단방향 관계, user 삭제되면 일기도 삭제
    @JoinColumn(name = "user_id")
    private User user;  // 유저 pk (FK)

    public Diary(String content, LocalDate created_at, String month, User user, String imageUrl, String videoUrl) {
        this.content = content;
        this.created_at = created_at;
        this.month = month;
        this.user = user;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }
}