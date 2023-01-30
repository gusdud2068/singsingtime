package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Song extends BaseEntity{
    private String title;
    private String singer;
    private LocalDateTime part1;
    private LocalDateTime part2;
    private LocalDateTime part3;
    private LocalDateTime part4;
}
