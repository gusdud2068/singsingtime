package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Builder
@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false)
    private String userId;
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    private String name;
    private String nickname;
    private String email;
    private int championCnt;
    private String genre;
    private int userRank;
    public String profileUrl;
}
