package com.ssafy.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
//import com.ssafy.db.repository.UserRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
//	@Autowired
//	UserRepositorySupport userRepositorySupport;
//
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getId());
		user.setName(userRegisterInfo.getName());
		user.setNickname(userRegisterInfo.getNickname());
		user.setPrefferedGenre(userRegisterInfo.getPrefferedGenre());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		User user = new User();
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		try{
//			user = userRepositorySupport.findUserByUserId(userId).get();
			user = userRepository.findByUserId(userId).get();
		}
		catch (NoSuchElementException e) {
//			System.out.printf(e.getMessage());
			user = null;
		}
//		User user = userRepositorySupport.findUserByUserId(userId).orElseThrow(()->{
//			return new InvalidIDException("회원 찾기 실패");
//		});
		return user;
	}

	@Override
	public void updateUser(UserUpdatePatchReq userUpdateReq, String userId) {
		Optional<User> selectedUser = userRepository.findByUserId(userId);

		User getUser = selectedUser.get();
		getUser.setName(userUpdateReq.getName());
		getUser.setNickname(userUpdateReq.getNickname());
		getUser.setPrefferedGenre(userUpdateReq.getPrefferedGenre());
		getUser.setPassword(passwordEncoder.encode(userUpdateReq.getPassword()));

		User updatedUser = userRepository.save(getUser);
	}

	@Override
	public void deleteUser(String userId) {
		Optional<User> selectedUser = userRepository.findByUserId(userId);
		User getUser = selectedUser.get();
		userRepository.delete(getUser);
	}

	@Override
	public int checkUserID(String userId) {
		Optional<User> selectedUser = userRepository.findByUserId(userId);
		int res = selectedUser.isPresent() ? 1 : 0;
		System.out.println("user service 85 " + res);
		return res;
	}

	@Override
	public int checkUserNickname(String nickname) {
		Optional<User> selectedUser = userRepository.findByNickname(nickname);
		int res = selectedUser.isPresent() ? 1 : 0;
		System.out.println("user service 94 " + res);

		return res;
	}

//	@Override
//	public String getKaKaoAccessToken(String code){
//		String access_Token="";
//		String refresh_Token ="";
//		String reqURL = "https://kauth.kakao.com/oauth/token";
//
//		try{
//			URL url = new URL(reqURL);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//			//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//
//			//POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//			StringBuilder sb = new StringBuilder();
//			sb.append("grant_type=authorization_code");
//			sb.append("&client_id=c3eba1bf9cae0faefa55f3a8e7e58525"); // TODO REST_API_KEY 입력
//			sb.append("&redirect_uri=http://localhost:8080/api/v1/auth/kakao"); // TODO 인가코드 받은 redirect_uri 입력
//			sb.append("&code=" + code);
//			bw.write(sb.toString());
//			bw.flush();
//
//			//결과 코드가 200이라면 성공
//			int responseCode = conn.getResponseCode();
//			System.out.println("responseCode : " + responseCode);
//			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line = "";
//			String result = "";
//
//			while ((line = br.readLine()) != null) {
//				result += line;
//			}
//			System.out.println("response body : " + result);
//
//			//Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//			JsonParser parser = new JsonParser();
//			JsonElement element = parser.parse(result);
//
//			access_Token = element.getAsJsonObject().get("access_token").getAsString();
//			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//
//			System.out.println("access_token : " + access_Token);
//			System.out.println("refresh_token : " + refresh_Token);
//
//			br.close();
//			bw.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//		return access_Token;
//	}
//
//	@Override
//	public void createKakaoUser(String token) {
//
//		String reqURL = "https://kapi.kakao.com/v2/user/me";
//
//		//access_token을 이용하여 사용자 정보 조회
//		try {
//			URL url = new URL(reqURL);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//			conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송
//
//			//결과 코드가 200이라면 성공
//			int responseCode = conn.getResponseCode();
//			System.out.println("responseCode : " + responseCode);
//
//			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line = "";
//			String result = "";
//
//			while ((line = br.readLine()) != null) {
//				result += line;
//			}
//			System.out.println("response body : " + result);
//
//			//Gson 라이브러리로 JSON파싱
//			JsonParser parser = new JsonParser();
//			JsonElement element = parser.parse(result);
//
//			Long id = element.getAsJsonObject().get("id").getAsLong();
//			boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
//			String email = "";
//			if (hasEmail) {
//				email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
//			}
//
//			System.out.println("id : " + id);
//			System.out.println("email : " + email);
//
//			br.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
