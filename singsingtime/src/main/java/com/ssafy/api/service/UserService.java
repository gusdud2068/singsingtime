package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.db.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
	void updateUser(UserUpdatePatchReq userUpdateReq, String userId);
	void deleteUser(String userId);

	int checkUserID(String userId);
//	String getKaKaoAccessToken(String code);
//	void createKakaoUser(String token);
	int checkUserNickname(String nickname);

    void addMyProfile(String userId, MultipartFile profileImg) throws IOException;
}
