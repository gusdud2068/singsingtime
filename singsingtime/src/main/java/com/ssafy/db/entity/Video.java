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
    private String title;
    private String description;
    private String url;
    private int likeCnt;

    private String userId;
}
