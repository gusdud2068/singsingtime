package com.ssafy.api.response;

import com.ssafy.db.entity.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class PlayroomStatusRes {
    List<Song> championSongList =new ArrayList<>();
    int winCnt;
}
