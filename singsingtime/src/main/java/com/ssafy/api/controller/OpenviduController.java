package com.ssafy.api.controller;

//import com.ssafy.api.service.DiaryService;
import com.ssafy.api.service.PlayroomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.db.entity.User;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/openvidu")
public class OpenviduController {

	// 방 정원 6명
	private final int MAX_MEMBER_CNT = 50;
	@Value("${OPENVIDU_URL}")
	private String OPENVIDU_URL;

	@Value("${OPENVIDU_SECRET}")
	private String OPENVIDU_SECRET;

	private OpenVidu openvidu;

	@Autowired
	private PlayroomService playroomService;
	@Autowired
	private UserService userService;
//private

	@PostConstruct
	public void init() {
		this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
	}

	/**
	 * @param params The Session properties
	 * @return The Session ID
	 */
	@PostMapping("/sessions")
	public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params, Authentication auth)
			throws OpenViduJavaClientException, OpenViduHttpException {
		SsafyUserDetails userDetails = (SsafyUserDetails)auth.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);

		SessionProperties properties = SessionProperties.fromJson(params).build();
		Session session = openvidu.createSession(properties);

		// 지금 요청 방식이 같은 sessionId를 쳐서 같은 session에 접속하는 방식
		// 유저들이 접속할때마다 여기로 요청 들어올건데 같은 sessionId를 가지고 계속 createPlayRoom 할 수는 없음
		// playroom 테이블 확인해서 sessionId가 없는 상태면 데이터 만들고, 있는 상태면 넘어가는 방식으로 설계함

		if(playroomService.checkPlayRoom(params) > 0){
			return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
		}
		else{
			// 하나도 없으면 만들고 return
			playroomService.createPlayRoom(params, user);
			return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
		}
	}

	/**
	 * @param sessionId The Session in which to create the Connection
	 * @param params    The Connection properties
	 * @return The Token associated to the Connection
	 */
	@PostMapping("/sessions/{sessionId}/connections")
	public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
												   @RequestBody(required = false) Map<String, Object> params)
			throws OpenViduJavaClientException, OpenViduHttpException {
		Session session = openvidu.getActiveSession(sessionId);
		if (session == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

//		if(playroomService.checkPlayRoomMemberCnt(sessionId) >= MAX_MEMBER_CNT){
//			playroomService.addPlayRoomMemberCnt(sessionId);
//			return new ResponseEntity<>("방 정원 초과입니다. 다른 노래방을 이용해주세요", HttpStatus.OK);
//		}

		playroomService.addPlayRoomMemberCnt(sessionId);
		System.out.println("params = " + params);
//		params.put("type", ConnectionType.WEBRTC);
//		params.put("data", "user_data");
//		params.put("role", OpenViduRole.PUBLISHER);

//		KurentoOptions kurentoOptions = new KurentoOptions.Builder()
//								.allowedFilters(new String[]{"GStreamerFilter", "FaceOverlayFilter"})
//								.build();
//		params.put("kurentoOptions", kurentoOptions);

		ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
		Connection connection = session.createConnection(properties);

		// 얼굴 마스크 필터 적용 코드
//		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
//				.type(ConnectionType.WEBRTC)
//				.data("user_data")
//				.role(OpenViduRole.PUBLISHER)
//				.kurentoOptions(
//						new KurentoOptions.Builder()
//								.allowedFilters(new String[]{"GStreamerFilter", "FaceOverlayFilter"})
//								.build())
//				.build();
//		Connection connection = session.createConnection(connectionProperties);
//		String token = connection.getToken(); // Send this string to the client side


		return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
	}
}

