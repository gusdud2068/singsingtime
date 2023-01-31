package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video extends BaseEntity {
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private int likeCnt;
    private String title;
    private String description;
}
