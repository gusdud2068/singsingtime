package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PlayroomReq")
public class PlayroomCreateReq {
    private String customSessionId;
}
