package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Playroom extends BaseEntity {
    private LocalDateTime callStartTime;

    private LocalDateTime callEndTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genreId")
    private Genre genre;
    @ManyToOne(fetch = FetchType.LAZY)
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
}
