package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video extends BaseEntity {
    private String title;
    private String description;
    private String url;
    private int likeCnt;
    private int viewCnt;
    @ManyToOne(fetch = FetchType.LAZY) // 여러 영상 - 하나의 유저
    @JoinColumn(name="userId")
    private User user;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY) // 한 영상 글 - 여러 댓글
    // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니에요) DB에 칼럼을 만들지 마세요
    // DB에 들어가 있는게 아니라 SELECT를 위한 코드
    @JsonIgnoreProperties({"video"})
    private List<Reply> replys;

}
