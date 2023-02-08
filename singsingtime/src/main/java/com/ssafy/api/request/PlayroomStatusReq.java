package com.ssafy.api.request;

import com.ssafy.db.entity.Song;
import lombok.Data;
import lombok.Getter;

@Data
public class PlayroomStatusReq {
    int sessionId;
    String championId;
    String title;
//    String singer;
}
