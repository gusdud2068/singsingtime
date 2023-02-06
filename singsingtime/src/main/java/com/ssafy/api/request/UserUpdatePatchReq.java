package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 개인정보수정 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdateRequest")
public class UserUpdatePatchReq {
	@ApiModelProperty(name="유저 Password", example="your_password")
	private String password;
	@ApiModelProperty(name="유저 name", example="김개명")
	private String name;
	@ApiModelProperty(name="유저 nickname", example="안나")
	private String nickname;
	@ApiModelProperty(name="유저 email", example="change@ssafy.com")
	private String email;
	@ApiModelProperty(name="유저 선호장르", example="트로트")
	private String genre;
}
