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
	String userId;

	@ApiModelProperty(name="User password")
	String password;

	@ApiModelProperty(name="User nickname")
	String nickname;

	@ApiModelProperty(name="User championCnt")
	int championCnt;

	@ApiModelProperty(name="User rank")
	int rank;
	//노래 리스트도 추가해야함. 고민중.
	@ApiModelProperty(name="User songList")
	List<Song> songList;
	
	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		res.setPassword(user.getPassword());
		res.setNickname(user.getNickname());
		res.setChampionCnt(user.getChampionCnt());
		res.setRank(user.getUserRank());
//		List<UserSong> songList = user.getSongList();
//		res.setSongList(user.getSongList());
		return res;
	}
}
