package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 비디오 댓글 수정 API ([PUT]) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("ReplyUpdatePutReq")
public class ReplyUpdatePutReq {

	public Long replyId;
	@ApiModelProperty(name="비디오 context", example="엘사")
	private String context;
}
