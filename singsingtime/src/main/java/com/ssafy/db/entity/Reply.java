package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity {
    @Column(nullable = false)
    private String context;

    private int likeCnt;
    
    @ManyToOne // 여러 답글 - 한 영상글
    @JoinColumn(name = "videoId")
    private Video video;

    @ManyToOne // 여러 답글 - 한 유저
    @JoinColumn(name = "userId")
    private User user;
}
