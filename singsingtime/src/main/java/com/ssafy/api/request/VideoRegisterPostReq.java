package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 비디오 업로드 API ([POST] /diary/video) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("VideoRegisterPostRequest")
public class VideoRegisterPostReq {
	@ApiModelProperty(name="비디오 ID", example="ssafy_web")
	private String id;
	@ApiModelProperty(name="비디오 title", example="김싸피")
	private String title;
	@ApiModelProperty(name="비디오 context", example="엘사")
	private String context;
}
