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
    private int sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genreId")
    private Genre genre;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User user;

    private String thumbnailUrl;
    private String title;
    private String curPlay;
    private int userCnt;
    private int challengerCnt;
    private String champion;
    private String challenger;
    private int recommendCnt;

    @Builder
    public Playroom(int sessionId, String title, User user){
        this.sessionId=sessionId;
        this.title=title;
        this.user=user;
    }

}
