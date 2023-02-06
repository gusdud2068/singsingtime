package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 사이트 노래 등록 ([POST] /users/songs) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("SongRegisterPostRequest")
public class SongRegisterPostReq {
	private String title;
	private String singer;
	private int part1;
	private int part2;
	private int part3;
	private int part4;
}
