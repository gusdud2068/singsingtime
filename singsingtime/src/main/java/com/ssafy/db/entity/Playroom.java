package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Playroom extends BaseEntity {
    private LocalDateTime callStartTime;

    private LocalDateTime callEndTime;
    @Unique
    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genreId")
    private Genre genre;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User user;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private PlayroomStatus playroomStatus;
    private String thumbnailUrl;
    private String title;
    private String curPlay;
    private int userCnt;
    private int winCnt;
    private String champion;
    private String challenger;
    private int recommendCnt;

    @Builder
    public Playroom(String sessionId, String title, String champion, int userCnt, User user){
        this.sessionId=sessionId;
        this.title= title;
        this.champion = user.getUserId();
        this.userCnt = 1;
        this.user= user;
        this.setPlayroomStatus(PlayroomStatus.STANDBY);
    }

}
