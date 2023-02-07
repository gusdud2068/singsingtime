package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PlayroomReq")
public class PlayroomReq {
    private String title;
    private int sessionId;
    private long ownerId;
}
