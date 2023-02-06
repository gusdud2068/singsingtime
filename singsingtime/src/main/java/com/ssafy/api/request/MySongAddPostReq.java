package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 플레이리스트 등록 ([POST] /users/me/songs) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("MySongAddPostReq")
public class MySongAddPostReq {
	private String songId;
}
