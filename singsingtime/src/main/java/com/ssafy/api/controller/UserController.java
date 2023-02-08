package com.ssafy.api.controller;

import com.ssafy.api.request.MySongAddPostReq;
import com.ssafy.api.request.SongRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.SongService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Song;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private SongService songService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);
		
		return ResponseEntity.status(200).body(UserRes.of(user));
	}


	@GetMapping("/id/{userId}")
	@ApiResponses({
			@ApiResponse(code = 200, message = "사용 가능한 ID 입니다."),
			@ApiResponse(code = 409, message = "이미 존재하는 사용자 ID 입니다.")
	})
	public ResponseEntity<? extends BaseResponseBody> checkIDDuplication(@PathVariable String userId){
		System.out.println("id check 80");
		if(userService.checkUserID(userId) > 0){
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID 입니다."));
		}
		else{
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용 가능한 ID 입니다."));
		}
	}

	@GetMapping("/nickname/{nickname}")
	@ApiResponses({
			@ApiResponse(code = 200, message = "사용 가능한 닉네임 입니다."),
			@ApiResponse(code = 409, message = "이미 존재하는 닉네임 입니다.")
	})
	public ResponseEntity<? extends BaseResponseBody> checkNicknameDuplication(@PathVariable String nickname){

		if(userService.checkUserNickname(nickname) > 0){
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 닉네임 입니다."));
		}
		else{
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용 가능한 닉네임 입니다."));
		}
	}


	@PutMapping("/{userId}")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> update(@PathVariable String userId, @RequestBody UserUpdatePatchReq user){
		userService.updateUser(user, userId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}


	@DeleteMapping("/{userId}")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success"),
	})
	public ResponseEntity<? extends BaseResponseBody> delete(@PathVariable String userId){
		userService.deleteUser(userId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PostMapping("/songs")
	public ResponseEntity<? extends BaseResponseBody> registerSong(@RequestBody SongRegisterPostReq registerInfo){
		songService.registerSong(registerInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	@GetMapping("/songs")
	public ResponseEntity<List<Song>> getAllSongList(){
		List<Song> songList =songService.getAllSongList();
		return ResponseEntity.status(200).body(songList);
	}

	@PostMapping("me/songs")
	public ResponseEntity<? extends BaseResponseBody> addMySongList(@ApiIgnore Authentication authentication, @RequestBody MySongAddPostReq addInfo){
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		songService.addMySong(userId, addInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	@GetMapping("me/songs")
	public ResponseEntity<List<Song>> getMySongList(@ApiIgnore Authentication authentication){
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);
		// fk 필요해서 getId 사용
		List<Song> songList = songService.getMySongList(user.getId());
		return ResponseEntity.status(200).body(songList);
	}

	@DeleteMapping("me/songs/{userSongId}")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success"),
	})
	public ResponseEntity<? extends BaseResponseBody> deleteMySong(@PathVariable Long userSongId){
		songService.deleteMySong(userSongId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
}
