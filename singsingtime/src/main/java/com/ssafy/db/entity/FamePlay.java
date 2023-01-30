package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FamePlay extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String title;
    private String thumbnailUrl;

    @Lob
    private String filePath;
    private int viewCnt;
    private int viewRank;
    private int recommendCnt;
    private int recommendRank;
}
