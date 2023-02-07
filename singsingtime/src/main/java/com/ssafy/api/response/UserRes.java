package com.ssafy.api.response;

import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserSong;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="User ID")
	private String userId;

//	@ApiModelProperty(name="User password")
//	private String password;

	@ApiModelProperty(name="User nickname")
	private String nickname;
	@ApiModelProperty(name="User nickname")
	private String name;

	@ApiModelProperty(name="유저 email", example="change@ssafy.com")
	private String email;
	@ApiModelProperty(name="선호장르", example="발라드")
	private String genre;
	@ApiModelProperty(name="User championCnt")
	private int championCnt;

	@ApiModelProperty(name="User rank")
	private int userRank;
	//노래 리스트도 추가해야함. 고민중.

	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		res.setNickname(user.getNickname());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setGenre(user.getGenre());
		res.setChampionCnt(user.getChampionCnt());
		res.setUserRank(user.getUserRank());
		return res;
	}
}
